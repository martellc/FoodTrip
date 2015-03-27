package com.foodtrip.ftcontroller;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftcontroller.exception.FoodtripError;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.model.Step;
import com.foodtrip.ftmodeldb.repo.OrderRepository;
import com.foodtrip.ftmodeldb.repo.ProductRepository;
import com.foodtrip.ftmodeldb.repo.StepRepository;
import com.foodtrip.ftmodelws.OrderWS;

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
		
		if(order.getProduct().getFarm() == null) {
			logger.error("Invalid farm");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}
		
		graph.beginTx();
		try {
			Order orderDB = ModelUtils.toOrderDB(order);
			
			boolean newProduct = orderDB.getOrderProductRel().getProduct().getId() == null;			
			Product product = productRepo.save(orderDB.getOrderProductRel().getProduct());
			
			if(newProduct) {
				orderDB.getOrderProductRel().setProduct(product);
			}
			
			Float alt = product.getAlt() == null? product.getFarm().getAlt(): product.getAlt();
			Float lng = product.getLng() == null? product.getFarm().getLng(): product.getLng();
			Float lat = product.getLat() == null? product.getFarm().getLat() :product.getLat();
			
			Step step = new Step();
			step.setAlt(alt);
			step.setLat(lat);
			step.setLng(lng);
			step.setCompanyID(product.getFarm().getId());
			
			orderDB.setStep(step);
			
			StepRepo.save(step);
			Order updatedOrder = repo.save(orderDB);
			
			return ModelUtils.toOrderWS(updatedOrder);
		}catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}finally {
			
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
