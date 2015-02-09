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
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.OrderRepository;
import com.foodtrip.ftmodelws.CompanyWS;
import com.foodtrip.ftmodelws.FarmWS;
import com.foodtrip.ftmodelws.FoodStepWS;
import com.foodtrip.ftmodelws.TripView;

@Stateless
public class FTTripController extends FTController {
	private static final Logger logger = Logger.getLogger(FTTripController.class);
	
	private Collection<Company> getCompaniesPath(Long endCompany, Long orderID) {

		if (orderID == null || endCompany == null) {
			logger.error("Invalid null value");
			return null;
		}
		
		OrderRepository orderRepository = connector.getOrderRepository();
		CompanyRepository companyRepository = connector.getCompanyRepository();

		//check the end point existance
		Company endPoint = companyRepository.findOne(endCompany);
		if(endPoint == null) {
			logger.error("Invalid end point");
			return null;
		}
		
		Order order = orderRepository.findOne(orderID);
		if (order == null) {
			logger.error("Invalid ID. Could not retrieve order inside the db");
			return null;
		}
		
		//get the path
		Iterable<Company> companies = companyRepository.getCompaniesPath(endCompany, orderID);
		Iterator<Company> companyIt = companies.iterator();
		
		List<Company> list = new ArrayList<Company>();
		
		list.add(endPoint);
		while (companyIt.hasNext()) {
			list.add(companyIt.next());
		}
		
		
		return list;
	}
	
	public TripView getTrip(Long orderID,Long endCompany) {		
		TripView t = new TripView();
		
		if (orderID == null || endCompany == null) {
			logger.error("Invalid null value");
			return null;
		}
		
		CompanyRepository companyRepository = connector.getCompanyRepository();
		Company endPoint = companyRepository.findOne(endCompany);
		if(endPoint == null) {
			logger.error("Invalid end point");
			return null;
		}

		OrderRepository orderRepository = connector.getOrderRepository();
		Order order = orderRepository.findOne(orderID);
		if (order == null) {
			logger.error("Invalid ID. Could not retrieve order inside the db");
			return null;
		}
		
		List<FoodStepWS> steps = getSteps(endCompany, orderID);
		
		t.setSteps(steps);
		Product product = order.getOrderProductRel().getProduct();
		t.setProduct(ModelUtils.toProductWS(product));
		
		Farm farm = product.getFarm();
		FarmWS farmWS = ModelUtils.toFarmWS(farm);
		t.setProducer(farmWS);

		//add starting point product
		Float productLat = product.getLat();
		Float productLng = product.getLng();
		Float productAlt = product.getAlt();
		String infoWindow = "1 - " + product.getName() + ". " + product.getHarvestDate();
		
		//add product production step
		FoodStepWS s = new FoodStepWS(1l,new CompanyWS(),productLat,productLng,productAlt,ModelUtils.intToDate(product.getHarvestDate()),null,null,FoodStepWS.MARKER_ICON_START,infoWindow);
		steps.add(s);
		Collections.reverse(steps);
			
		t.setPath(createPath(steps));
		
		return t;
	}
	
	private String createPath(List<FoodStepWS> steps) {
		String path = "[ ";
		
		
		for(FoodStepWS f : steps) {
			if(f.getLat()== null || f.getLng()== null) {
				continue;
			}
			path+= "["+ f.getLat() +"," + f.getLng() +"],";
		}
		if(path.endsWith(",")) {
			path = path.substring(0,path.length()-1);
		}
		
		path+=" ]";
		
		return path;
	}

	private List<FoodStepWS> getSteps(Long endCompany, Long orderID) {
		List<FoodStepWS> steps = new ArrayList<FoodStepWS>();
		Collection<Company> companies = getCompaniesPath(endCompany, orderID);
		//this a generic id
		long i = companies.size() + 1;
		Company previousCompany = null;
		for(Company c : companies) {
			
			boolean isLast = i == companies.size() + 1;
			
			String icon = FoodStepWS.MARKER_ICON;
			if (isLast) {
				icon = FoodStepWS.MARKER_ICON_END;
			}
			
			String infoWindow = i + " - " + c.getName();
			//if you are at the beginning or at the end of a trip you don't have relation information
			if (c.getCompanyToCompanyRel() == null || previousCompany == null) {
				FoodStepWS s = new FoodStepWS(new Long(i),ModelUtils.toCompanyWS(c),c.getLat(),c.getLng(),c.getAlt(),null,null,null,icon,infoWindow);
				steps.add(s);
				previousCompany = c;
				i--;
				continue;
			}
			
			//find the relation between two companies: the relation contains some useful information
			CompanyToCompanyRel cToc = findCompanyToCompanyRel(c,previousCompany,orderID);
			if(cToc == null) {
				previousCompany = c;
				logger.error("Unable to find relation: " + previousCompany.getCompanyID() + "," + c.getId());
				continue;
			}
			
			FoodStepWS s = new FoodStepWS(new Long(i),ModelUtils.toCompanyWS(c),cToc.getLat(),cToc.getLng(),cToc.getAlt(),cToc.getDate(),cToc.getQuantity(),cToc.getAmount(),icon,infoWindow);
			steps.add(s);
			
			previousCompany = c;
			i--;
		}
		
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
			if (cToC.getStart().getId().equals(start.getId()) && cToC.getOriginalOrderID().equals(orderID)) {
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
			/*get the trip current end-point*/
			Long previousEndPoint = endCompany;
			Company previousEPCompany = companyRepository.findOne(previousEndPoint);
			Company newCompany = companyRepository.findOne(company.getId());
			
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
			rel.setEnd(newCompany);
			rel.setOriginalOrderID(orderID);
			if(newCompany.getCompanyToCompanyRel() == null) {
				newCompany.setCompanyToCompanyRel(new HashSet<CompanyToCompanyRel>());
			}
			newCompany.getCompanyToCompanyRel().add(rel);
			newCompany = companyRepository.save(newCompany);
			
			//update order end-point with the id of the new company
			order.getEndPoint().add(newCompany.getId());
			
			orderRepository.save(order);
		} catch(Exception e) {
			logger.error("Error: ", e);
		}
	}
}