package com.foodtrip.ftcontroller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;

import com.foodtrip.ftmodeldb.Neo4JConnector;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.model.CompanyToCompanyRel;
import com.foodtrip.ftmodeldb.model.Farm;
import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.model.OrderProductRel;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.OrderRepository;
import com.foodtrip.ftmodelws.CompanyWS;
import com.foodtrip.ftmodelws.Step;
import com.foodtrip.ftmodelws.TripView;

@Stateless
public class FTTripController {
	public static Neo4JConnector connector;
	private static final Logger logger = Logger.getLogger(FTTripController.class);

	public Order startTrip(Product p, Farm farm) {
		Order order = new Order();
		order.setFarm(farm);
		OrderProductRel op = new OrderProductRel(order,p);
		order.setOrderProductRel(op);

		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository orderRepository = connector.getOrderRepository();
		graph.beginTx();
		try {
			order = orderRepository.save(order);
			return order;
		} catch(Exception e) {
			logger.error("Error: ", e);
		}

		return null;
	}
	
	public Collection<Company> getCompaniesPath(Long orderID) {

		if (orderID == null) {
			logger.error("Invalid null order");
			return null;
		}
		
		OrderRepository orderRepository = connector.getOrderRepository();
		CompanyRepository companyRepository = connector.getCompanyRepository();

		Order order = orderRepository.findOne(orderID);
		if (order == null) {
			logger.error("Invalid ID. Could not retrieve order inside the db");
			return null;
		}
		
		return companyRepository.getCompaniesPath(order.getFarm().getId(), order.getEndPoint(), orderID);
	}
	
	public TripView getTrip(Long orderID) {		
		TripView t = new TripView();
		Collection<Step> steps = getSteps(orderID);
		
		OrderRepository orderRepository = connector.getOrderRepository();
		Order order = orderRepository.findOne(orderID);
		if (order == null) {
			logger.error("Invalid ID. Could not retrieve order inside the db");
			return null;
		}
		
		t.setSteps(steps);
		t.setProduct(ModelUtils.toProductWS(order.getOrderProductRel().getProduct()));
		t.setProducer(ModelUtils.toFarmWS(order.getFarm()));
		
		return t;
	}
	
	private Collection<Step> getSteps(Long orderID) {
		List<Step> steps = new ArrayList<Step>();
		Collection<Company> companies = getCompaniesPath(orderID);
		
		
		//this a generic id
		long i = 0;
		Company previousCompany = null;
		for(Company c : companies) {
			if (c.getCompanyToCompanyRel() == null) {
				previousCompany = c;
				continue;
			}
			
			CompanyToCompanyRel cToc = findCompanyToCompanyRel(previousCompany,c,orderID);
			if(cToc == null) {
				previousCompany = c;
				logger.error("Unable to find relation: " + previousCompany.getCompanyID() + "," + c.getId());
				continue;
			}
			
			Step s = new Step(new Long(i),c,cToc.getLat(),cToc.getLng(),cToc.getAlt(),cToc.getDate(),cToc.getQuantity(),cToc.getAmount());
			steps.add(s);
			
			previousCompany = c;
			i++;
		}
		
		Collections.sort(steps);
		return steps;
	}
	
	//TODO:USE A QUERY FOR IT
	private CompanyToCompanyRel findCompanyToCompanyRel(
			Company start, Company end, Long orderID) {
		if (start == null) {
			logger.debug("startPointNull");
			return null;
		}
		
		for(CompanyToCompanyRel cToC : end.getCompanyToCompanyRel()) {
			if (cToC.getStart().equals(start) && cToC.getOriginalOrderID().equals(orderID)) {
				return cToC;
			}
		}
		
		return null;
	}

	public void addStep(Long orderID, CompanyWS company) {
		
		if (orderID == null) {
			logger.error("Invalid null order");
			return;
		}
		
		/*get an order*/
		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository orderRepository = connector.getOrderRepository();
		CompanyRepository companyRepository = connector.getCompanyRepository();
		
		Order order = orderRepository.findOne(orderID);
		if (order == null) {
			logger.error("Invalid ID. Could not retrieve order inside the db");
			return;
		}
		
		graph.beginTx();
		try {
			/*save company if needed*/
			Company c = companyRepository.save(ModelUtils.toCompanyDB(company));
			
			/*get the trip current end-point*/
			Long previousEndPoint = order.getEndPoint();
			Company previousEPCompany = null;
			if(order.getEndPoint() == null) {
				previousEPCompany = order.getFarm();
			} else {
				previousEPCompany = companyRepository.findOne(previousEndPoint);
			}
			
			/*create a new relation between old and new end-point*/
			CompanyToCompanyRel rel = new CompanyToCompanyRel();
			rel.setStart(previousEPCompany);
			rel.setEnd(c);
			rel.setOriginalOrderID(orderID);
			if(c.getCompanyToCompanyRel() == null) {
				c.setCompanyToCompanyRel(new HashSet<CompanyToCompanyRel>());
			}
			c.getCompanyToCompanyRel().add(rel);
			companyRepository.save(c);
			
			//update order end-point with the id of the new company
			order.setEndPoint(c.getId());
			orderRepository.save(order);
		} catch(Exception e) {
			logger.error("Error: ", e);
		}
	}
}