package com.foodtrip.ftcontroller;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftcontroller.exception.FoodtripError;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.model.Step;
import com.foodtrip.ftmodeldb.repo.OrderRepository;
import com.foodtrip.ftmodeldb.repo.ProductRepository;
import com.foodtrip.ftmodeldb.repo.StepRepository;
import com.foodtrip.ftmodelws.CompanyWS;
import com.foodtrip.ftmodelws.FarmWS;
import com.foodtrip.ftmodelws.OrderWS;
import com.foodtrip.ftmodelws.PointWS;

@Stateless
public class FTOrderController extends FTController {
	private static final Logger logger = Logger.getLogger(FTOrderController.class);

	@Transactional
	public OrderWS createOrder(OrderWS order) throws FoodtripException {
		return updateOrder(order);
	}
	

	@Transactional
	public OrderWS updateOrder(OrderWS order) throws FoodtripException {
		if(order == null) {
			logger.error("Null order");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
		
		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository repo = connector.getOrderRepository();
		ProductRepository productRepo = connector.getProductRepository();
		StepRepository StepRepo = connector.getStepRepository();
		
		if (order.getProduct() == null ) {
			logger.error("Invalid product");
			throw new FoodtripException(FoodtripError.INVALID_PRODUCT.getCode());
		}
		
		CompanyWS farm = order.getProduct().getFarm();
		if(farm == null) {
			logger.error("Invalid farm");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}
		new FTCompanyController().checkCompany(farm);
		
		if(order.getNextStep() == null) {
			logger.error("Invalid next step");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}
		
		Transaction tx = graph.beginTx();
		try {
			Order orderDB = ModelUtils.toOrderDB(order);
		
			//update the product: (always better to save it again)
			Product p = orderDB.getOrderProductRel().getProduct();
			Product product = productRepo.save(p);
			orderDB.getOrderProductRel().setProduct(product);
			
			Float alt = product.getAlt() == null? product.getFarm().getAlt(): product.getAlt();
			Float lng = product.getLng() == null? product.getFarm().getLng(): product.getLng();
			Float lat = product.getLat() == null? product.getFarm().getLat() :product.getLat();
			
			//create first step (lat and lng are from product)
			Step step = new Step();
			step.setAlt(alt);
			step.setState(Step.STATE_CONFIRMED);
			step.setLat(lat);
			step.setLng(lng);
			step.setCompanyID(product.getFarm().getId());
			orderDB.setStep(step);
			StepRepo.save(step);
			
			//save the order
			Order updatedOrder = repo.save(orderDB);
			
			//add the next step (the company who bought from the farm)
			PointWS point = order.getNextStep().getPoint();
			CompanyWS seller = order.getNextStep().getCompany();
			new FTTripController().addStep(step.getId(), updatedOrder.getId(), seller, point,false);
			
			return ModelUtils.toOrderWS(updatedOrder);
		}catch(Exception e) {
			tx.failure();
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}finally {
			tx.close();
		}
	}
	
	@Transactional
	public void removeOrder(OrderWS order) throws FoodtripException {
		
		if(order == null) {
			logger.error("Null order");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
		
		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository repo = connector.getOrderRepository();
		graph.beginTx();
		try {
			//TODO: CHECK FOR RELATION REMOVAL
			repo.delete(ModelUtils.toOrderDB(order));
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}
	}
	
	@Transactional
	public OrderWS getOrder(Long id) throws FoodtripException {
		if(id == null) {
			logger.error("Null id");
			throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
		}
		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository repo = connector.getOrderRepository();
		graph.beginTx();
		try {
			Order c = repo.findOne(id);
			if(c == null) {
				logger.error("Null company");
				throw new FoodtripException(FoodtripError.INVALID_ORDER.getCode());
			}
		
			return ModelUtils.toOrderWS(c);
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}
	}
}
