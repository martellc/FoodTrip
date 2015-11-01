package com.foodtrip.ftcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import com.foodtrip.ftmodeldb.model.Notification;
import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.model.Step;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.NotificationRepository;
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
	
	public static final String DEFAULT_TRIP = "676-690";
	public static final long DEFAULT_TRIP_ENDPOINT = 690l;
	public static final long DEFAULT_TRIP_ORDER = 676l;
	
	public TripView getTrip(Long orderID,Long endStepID) throws FoodtripException {		
		TripView t = new TripView();
		boolean isDefault = false;
		
		if (orderID == null) {
			logger.error("Invalid null value");
			orderID = DEFAULT_TRIP_ORDER;
			endStepID = DEFAULT_TRIP_ENDPOINT;
			isDefault = true;
		}
		
		//L'end point non è sempre necessario
		Step endPoint = null;
		StepRepository stepRepository = connector.getStepRepository();
		if (endStepID != null) {
			stepRepository.findOne(endStepID);
			if(endPoint == null) {
				logger.error("No end point found");
				isDefault = true;
			}
		}
		
		OrderRepository orderRepository = connector.getOrderRepository();
		Order order = orderRepository.findOne(orderID);
		if (order == null || order.getStep() == null) {
			logger.error("Invalid order id or step null");
			isDefault = true;
		}
		
		//if the user insert a wrong trip id use the default
		if(isDefault) {
			t.setDefault(true);
			orderID = DEFAULT_TRIP_ORDER;
			endStepID = DEFAULT_TRIP_ENDPOINT;
			endPoint = stepRepository.findOne(DEFAULT_TRIP_ENDPOINT);
			order = orderRepository.findOne(DEFAULT_TRIP_ORDER);
		}
		
		//create the food graph path
		Step firstStep = order.getStep();
		if(firstStep == null ) {
			logger.error("Invalid order id or step null");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());	
		}
		

		//add the path from the farmer to final distributor
		Node start = connector.getGraphDatabaseService().getNodeById(firstStep.getId());
		if(endPoint != null) {
			Node end = connector.getGraphDatabaseService().getNodeById(endStepID);
			TraversalDescription traversalDescription = Traversal.description()
					.breadthFirst()
					.relationships(Step.Rels.STEP)
					.evaluator(Evaluators.excludeStartPosition())
					.evaluator(Evaluators.endNodeIs(Evaluation.INCLUDE_AND_CONTINUE,Evaluation.EXCLUDE_AND_CONTINUE, end))
					.uniqueness(Uniqueness.NODE_PATH );
			List<String> mapPathsList = new ArrayList<String>();
			FoodStepWS graph = getFoodStepWS(start,end,traversalDescription,mapPathsList);
			List<FoodStepWS> flatFoodGraph = createFlatFoodGraph(graph);
			
			t.setFoodGraph(graph);
			t.setFlatFoodGraph(flatFoodGraph);
			t.setPaths(mapPathsList.toArray(new String[mapPathsList.size()]));
		}
		
		//add the product
		Product product = order.getOrderProductRel().getProduct();
		t.setProduct(ModelUtils.toProductWS(product));
		
		//add the farmer
		Company farm = product.getFarm();
		CompanyWS farmWS = ModelUtils.toCompanyWS(farm);
		t.setProducer(farmWS);

		return t;
	}
	
	private List<FoodStepWS> createFlatFoodGraph(FoodStepWS graph) {
		List<FoodStepWS> flatFoodGraph = new ArrayList<FoodStepWS>();
		
		flatFoodGraph.add(graph);
		createFlatFoodGraph(graph,flatFoodGraph);
		return flatFoodGraph;
	}

	private void createFlatFoodGraph(FoodStepWS graph,
			List<FoodStepWS> flatFoodGraph) {
		
		
		for(FoodStepWS child : graph.getChildren().values()) {
			flatFoodGraph.add(child);
		}
		
		for(FoodStepWS child : graph.getChildren().values()) {
			createFlatFoodGraph(child,flatFoodGraph);
		}
	}

	public FoodStepWS getFoodStepWS (Node start,Node end, TraversalDescription path, List<String> mapPathsList) throws FoodtripException {
		
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

		String startIconPath = FoodStepWS.MARKER_ICON_PATH + FoodStepWS.MARKER_ICON_NAME + "1.png";
		FoodStepWS graph = new FoodStepWS(step.getId(),ModelUtils.toCompanyWS(stepCompany),step.getLat(),step.getLng(),step.getAlt(),new Date(step.getDate()),null,null,startIconPath,stepCompany.getName());
		
		int index = 2;
		for (Path position : path.traverse(start)) {
			if(position.length() == 0) {
				continue;
			}
			String mapPath = null;
			boolean createPath = false;
			if(position.endNode().equals(end)) {
				createPath = true;
				mapPath = "[";
			}
			
			Node n = null;
			FoodStepWS previous = null;
			
			Iterator<Node> it = position.nodes().iterator();
			while (it.hasNext()) {
				n = it.next();
				
				if (n.equals(start)) {
					previous = graph;
					if(createPath && graph.getLat()!= null && graph.getLng()!= null) {
						mapPath += "["+ graph.getLat() +"," + graph.getLng() +"],";
					}
					continue;
				}
				
				if(previous == null) {
					previous = graph;
				}
				
				//crea uno StepGraph
				Step s = stepRepository.findOne(n.getId());
				
				
				if(createPath && s.getLat()!= null && s.getLng()!= null) {
					mapPath += "["+ s.getLat() +"," + s.getLng() +"],";
				}
				
				if(previous.getChildren().containsKey(s.getCompanyID())) {
					previous = previous.getChildren().get(s.getCompanyID());
					continue;
				}
					
				Company company = companyRepository.findOne(s.getCompanyID());
				if (company == null) {
					continue;
				}
				
				String icon = FoodStepWS.MARKER_ICON_PATH + FoodStepWS.MARKER_ICON_NAME + index +".png";
				FoodStepWS child = new FoodStepWS(s.getId(),ModelUtils.toCompanyWS(company),s.getLat(),s.getLng(),s.getAlt(),new Date(s.getDate()),null,null,icon,stepCompany.getName());
				child.setCompany(ModelUtils.toCompanyWS(company));
				child.setParentID(previous.getId());
				previous.getChildren().put(child.getCompany().getId(), child);
				previous = child;
				
				index++;
			}
			
			if(mapPath != null) {
				if(mapPath.endsWith(",")) {
					mapPath = mapPath.substring(0,mapPath.length()-1);
				}
				
				mapPath+=" ]";
				mapPathsList.add(mapPath);
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
	
	public void addStep(Long currentStepEndPointID, Long orderID, CompanyWS company,PointWS point,boolean useTransaction) throws FoodtripException {
		
		if (orderID == null) {
			logger.error("Invalid null order");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
		
		new FTCompanyController().checkCompany(company);
		
		/*get an order*/
		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository orderRepository = connector.getOrderRepository();
		CompanyRepository companyRepository = connector.getCompanyRepository();
		StepRepository stepRepository = connector.getStepRepository();
		NotificationRepository ntfsRepository = connector.getNotificationRepository();
		
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

		Transaction tx = null;
		if(useTransaction) {
			tx = graph.beginTx();
		}
		
		try {
			/*get the trip current end-point*/
			Step currentEndPoint = null;
			if(currentStepEndPointID == null) {
				currentEndPoint = order.getStep();
			}else {
				currentEndPoint = stepRepository.findOne(currentStepEndPointID);	
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
			newStep.setState(Step.STATE_TO_BE_CONFIRMED); 
			stepRepository.save(newStep);
			
			//define and save previous end point (for relation purpose)
			currentEndPoint.getNextSteps().add(newStep);
			stepRepository.save(currentEndPoint);
			
			//send notification to the seller (to confirm the new step)
			Notification n = new Notification();
			n.setCompanyID(currentEndPoint.getCompanyID()); //this is the previous company id
			n.setFoodTrip(orderID);
			n.setProductID(order.getOrderProductRel().getProduct().getId());
			n.setState(Notification.STATE_NEW);
			n.setStepID(newStep.getId());
			n = ntfsRepository.save(n);
			
			//add the notification and save the seller
			if(newCompany.getNotifications() == null) {
				newCompany.setNotifications(new HashSet<Notification>());
			}
			newCompany.getNotifications().add(n);
			companyRepository.save(newCompany);
			
		} catch(Exception e) {
			if(useTransaction){tx.failure();}
			logger.error("Error: ", e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		} finally {
			if(useTransaction) {tx.close();}
		}
	}

	public void confirmOrDenyStep(Long notificationID, CompanyWS company,boolean confirmed) throws FoodtripException {
		GraphDatabaseService graph = connector.graphDatabaseService();
		StepRepository stepRepository = connector.getStepRepository();
		NotificationRepository ntfsRepository = connector.getNotificationRepository();

		Transaction tx = null;
	
		if(notificationID == null) {
			throw new FoodtripException(FoodtripError.INVALID_STEP.getCode());
		}	
		if(company == null || company.getId() == null) {
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}
		new FTCompanyController().checkCompany(company);
		
		try {
			tx = graph.beginTx();	
			Notification n = ntfsRepository.findOne(notificationID);
			if(n == null || n.getId() == null) {
				throw new FoodtripException(FoodtripError.INVALID_STEP.getCode());
			}
			
			Step step = stepRepository.findOne(n.getStepID());
			if(step == null || step.getId() == null) {
				throw new FoodtripException(FoodtripError.INVALID_STEP.getCode());
			}
			
			if(step.getCompanyID().longValue() != company.getId().longValue()) {
				throw new FoodtripException(FoodtripError.INVALID_STEP_CONFIRM.getCode());
			}
			
			step.setState(confirmed ? Step.STATE_CONFIRMED : Step.STATE_INVALID);
			n.setState(confirmed ? Notification.STATE_STEP_CONFIRMED : Notification.STATE_STEP_INVALID);
			
			stepRepository.save(step);
			ntfsRepository.save(n);
			
			tx.success();
		} catch(Exception e) {
			tx.failure();
			logger.error("Error: ", e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		} finally {
			tx.close();
		}
	}
}