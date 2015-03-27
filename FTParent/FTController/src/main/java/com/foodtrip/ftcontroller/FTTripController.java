package com.foodtrip.ftcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.kernel.Traversal;

import com.foodtrip.ftcontroller.exception.FoodtripError;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.model.Step;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.OrderRepository;
import com.foodtrip.ftmodeldb.repo.StepRepository;
import com.foodtrip.ftmodelws.CompanyWS;
import com.foodtrip.ftmodelws.FoodStepWS;
import com.foodtrip.ftmodelws.PointWS;
import com.foodtrip.ftmodelws.StepWS;
import com.foodtrip.ftmodelws.TripView;

@Stateless
public class FTTripController extends FTController {
	private static final Logger logger = Logger.getLogger(FTTripController.class);
	
	public TripView getTrip(Long orderID,Long endStepID) throws FoodtripException {		
		TripView t = new TripView();
		
		if (orderID == null || endStepID == null) {
			logger.error("Invalid null value");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
		
		CompanyRepository companyRepository = connector.getCompanyRepository();
		Company endPoint = companyRepository.findOne(endStepID);
		if(endPoint == null) {
			logger.error("Invalid end point");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}

		OrderRepository orderRepository = connector.getOrderRepository();
		Order order = orderRepository.findOne(orderID);
		if (order == null || order.getStep() == null) {
			logger.error("Invalid order id or step null");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
		
		//create the food graph path
		Step firstStep = order.getStep();
		Node start = connector.getGraphDatabaseService().getNodeById(firstStep.getId());
		Node end = connector.getGraphDatabaseService().getNodeById(endStepID);
		TraversalDescription traversalDescription = Traversal.description()
				.breadthFirst()
				.relationships(Step.Rels.STEP)
				.evaluator(Evaluators.excludeStartPosition())
				.evaluator(Evaluators.endNodeIs(Evaluation.INCLUDE_AND_CONTINUE,Evaluation.INCLUDE_AND_CONTINUE, end))
				.uniqueness(Uniqueness.RELATIONSHIP_GLOBAL );
		FoodStepWS graph = getFoodStepWS(start,end,traversalDescription);
		t.setFoodGraph(graph);
		
		//add the product
		Product product = order.getOrderProductRel().getProduct();
		t.setProduct(ModelUtils.toProductWS(product));
		
		//add the farmer
		Company farm = product.getFarm();
		CompanyWS farmWS = ModelUtils.toCompanyWS(farm);
		t.setProducer(farmWS);

		//create paths for google maps plugin
		t.setPath(createPaths(graph));
		
		return t;
	}
	
	private String createPaths(FoodStepWS graph) {
		String path = "[ ";
		
		
//		for(FoodStepWS f : steps) {
//			if(f.getLat()== null || f.getLng()== null) {
//				continue;
//			}
//			path+= "["+ f.getLat() +"," + f.getLng() +"],";
//		}
//		if(path.endsWith(",")) {
//			path = path.substring(0,path.length()-1);
//		}
//		
		path+=" ]";
		
		return path;
	}

	public FoodStepWS getFoodStepWS (Node start,Node end, TraversalDescription path) throws FoodtripException {
		
		if(path == null || start == null) {
			throw new FoodtripException(FoodtripError.INVALID_TRIP.getCode());
		}
		
		StepRepository stepRepository = connector.getStepRepository();
		CompanyRepository companyRepository = connector.getCompanyRepository();
		
		Step step = stepRepository.findOne(start.getId());
		if(step == null) {
			throw new FoodtripException(FoodtripError.INVALID_TRIP.getCode());
		}
		
		Company stepCompany = companyRepository.findOne(step.getCompanyID()); 
		if(stepCompany == null) {
			throw new FoodtripException(FoodtripError.INVALID_TRIP.getCode());
		}

		FoodStepWS graph = new FoodStepWS(new Long(0),ModelUtils.toCompanyWS(stepCompany),step.getLat(),step.getLng(),step.getAlt(),new Date(step.getDate()),null,null,FoodStepWS.MARKER_ICON_START,stepCompany.getName());
		for (Path position : path.traverse(start)) {
			if(position.length() == 0) {
				continue;
			}

			Node n = null;
			FoodStepWS previous = null;

			int i = 1;
			Iterator<Node> it = position.nodes().iterator();
			while (it.hasNext()) {
				n = it.next();
				
				if (n.equals(start)) {
					previous = graph;
					continue;
				}
				
				if(previous == null) {
					previous = graph;
				}
				
				//crea uno StepGraph
				Step s = stepRepository.findOne(n.getId());
				Company company = companyRepository.findOne(s.getCompanyID());
				if (company == null) {
					continue;
				}
				
				String icon = n.equals(end) ? FoodStepWS.MARKER_ICON_END : FoodStepWS.MARKER_ICON;
				FoodStepWS child = new FoodStepWS(new Long(i),ModelUtils.toCompanyWS(company),s.getLat(),s.getLng(),s.getAlt(),new Date(s.getDate()),null,null,icon,stepCompany.getName());
				child.setCompany(ModelUtils.toCompanyWS(company));
				previous.getChildren().put(child.getCompany().getId(), child);
				previous = child;
				i++;
			}
		}
		
		return graph;
	}
	
	public List<StepWS> getCurrentSteps(Long orderID) throws FoodtripException {
		OrderRepository orderRepository = connector.getOrderRepository();
		StepRepository stepRepository = connector.getStepRepository();
		CompanyRepository companyRepository = connector.getCompanyRepository();
		if(orderID == null) {
			logger.error("invalid order id");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
		
		Order order = orderRepository.findOne(orderID);
		if (order == null || order.getStep() == null) {
			logger.error("Invalid ID or first step null. Could not retrieve order inside the db");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
		
		//create list of foodtrip leaves
		List<StepWS> steps = new ArrayList<StepWS>();
		
		//Get all the step in the graph: first step is excluded
		Iterable<Step> stepsIt = stepRepository.getCurrentSteps(order.getStep().getId());
		Iterator<Step> stepsIterator = stepsIt.iterator();
		while(stepsIterator.hasNext()) {
			Step s = stepsIterator.next();
			Company company = companyRepository.findOne(s.getCompanyID());
			if(company == null) {
				logger.error("Invalid company" + s.getCompanyID());
				continue;
			}
			StepWS step = new StepWS();
			step.setCompany(ModelUtils.toCompanyWS(company));
			step.setStepID(s.getId());
			steps.add(step);
		}
		
		return steps;
	}
	
	public void addStep(Long currentEndPointID, Long orderID, CompanyWS company,PointWS point) throws FoodtripException {
		
		if (orderID == null) {
			logger.error("Invalid null order");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
		
		/*get an order*/
		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository orderRepository = connector.getOrderRepository();
		CompanyRepository companyRepository = connector.getCompanyRepository();
		StepRepository stepRepository = connector.getStepRepository();
		
		
		Order order = orderRepository.findOne(orderID);
		if (order == null || order.getStep() == null) {
			logger.error("Invalid ID or first Step null. Could not retrieve order inside the db");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
	
		Company newCompany = companyRepository.findOne(company.getId());
		if(newCompany == null) {
			logger.error("Invalid company id");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}

		Transaction tx = graph.beginTx();
		try {
			/*get the trip current end-point*/
			Step currentEndPoint = null;
			if(currentEndPointID == null) {
				currentEndPoint = order.getStep();
			}else {
				currentEndPoint = stepRepository.findOne(currentEndPointID);	
			}
			
			if (currentEndPoint == null) {
				logger.error("Invalid end point");
				throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
			}
			
			//check if a step already exists
			for(Step s: currentEndPoint.getNextSteps()) {
				if(s.getCompanyID().equals(newCompany.getId())) {
					throw new FoodtripException(FoodtripError.DUPLICATE_STEP.getCode());	
				}
			}
			
			//define lat lng and alt
			Float lat = null;
			Float lng = null;
			Float alt = null;
			if (point!= null) {
				lat = point.getLat();
				lng = point.getLng();
				alt = point.getAlt();
			} else {
				lat = newCompany.getLat();
				alt = newCompany.getAlt();
				lng = newCompany.getLng();
			}
			
			//define and save a new step
			Step newStep = new Step(); 
			newStep.setCompanyID(newCompany.getId());
			newStep.setAlt(alt);
			newStep.setLng(lng);
			newStep.setLat(lat);
			stepRepository.save(newStep);
			
			//define and save previous end point (for relation purpose)
			currentEndPoint.getNextSteps().add(newStep);
			stepRepository.save(currentEndPoint);
		} catch(Exception e) {
			logger.error("Error: ", e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		} finally {
			tx.close();
		}
	}
}