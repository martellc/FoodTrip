package com.foodtrip.ftcontroller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;

import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.model.CompanyToCompanyRel;
import com.foodtrip.ftmodeldb.model.Farm;
import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.OrderRepository;
import com.foodtrip.ftmodelws.CompanyWS;
import com.foodtrip.ftmodelws.Step;
import com.foodtrip.ftmodelws.TripView;

@Stateless
public class FTTripController extends FTController {
	private static final Logger logger = Logger.getLogger(FTTripController.class);
	
	private Collection<Company> getCompaniesPath(Long endCompany, Long orderID) {

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
		
		//get the path
		Iterable<Company> companies = companyRepository.getCompaniesPath(endCompany, orderID);
		Iterator<Company> companyIt = companies.iterator();
		List<Company> list = new ArrayList<Company>();
		while (companyIt.hasNext()) {
			list.add(companyIt.next());
		}
		
		return list;
	}
	
	public TripView getTrip(Long endCompany, Long orderID) {		
		TripView t = new TripView();
		Collection<Step> steps = getSteps(endCompany, orderID);
		
		OrderRepository orderRepository = connector.getOrderRepository();
		Order order = orderRepository.findOne(orderID);
		if (order == null) {
			logger.error("Invalid ID. Could not retrieve order inside the db");
			return null;
		}
		
		t.setSteps(steps);
		t.setProduct(ModelUtils.toProductWS(order.getOrderProductRel().getProduct()));
		Farm farm = order.getOrderProductRel().getProduct().getFarm();
		t.setProducer(ModelUtils.toFarmWS(farm));
		
		return t;
	}
	
	private Collection<Step> getSteps(Long endCompany, Long orderID) {
		List<Step> steps = new ArrayList<Step>();
		Collection<Company> companies = getCompaniesPath(endCompany, orderID);
		
		
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

	public void addStep(Long endCompany, Long orderID, CompanyWS company) {
		
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
			Company c = ModelUtils.toCompanyDB(company);
			
			/*get the trip current end-point*/
			Long previousEndPoint = endCompany;
			Company previousEPCompany = companyRepository.findOne(previousEndPoint);
			if(previousEPCompany == null) {
				logger.error("Invalid end point");
				return;
			}

			if(!order.getEndPoint().contains(previousEndPoint)) {
				logger.error("Invalid end point");
				return;
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
			c = companyRepository.save(c);
			
			//update order end-point with the id of the new company
			order.getEndPoint().remove(previousEndPoint);
			order.getEndPoint().add(c.getId());
			
			orderRepository.save(order);
		} catch(Exception e) {
			logger.error("Error: ", e);
		}
	}
}