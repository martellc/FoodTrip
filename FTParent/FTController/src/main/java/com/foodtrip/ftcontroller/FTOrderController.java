package com.foodtrip.ftcontroller;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftmodeldb.model.Order;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.repo.OrderRepository;
import com.foodtrip.ftmodeldb.repo.ProductRepository;
import com.foodtrip.ftmodelws.OrderWS;

@Stateless
public class FTOrderController extends FTController {
	private static final Logger logger = Logger.getLogger(FTOrderController.class);

	@Transactional
	public OrderWS createOrder(OrderWS order) {
		return updateOrder(order);
	}
	

	@Transactional
	public OrderWS updateOrder(OrderWS order) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository repo = connector.getOrderRepository();
		ProductRepository productRepo = connector.getProductRepository();
	
		if (order.getProduct() == null ) {
			logger.error("Invalid product");
			return null;
		}
		
		if(order.getProduct().getFarm() == null) {
			logger.error("Invalid farm");
			return null;
		}
		
		graph.beginTx();
		try {
			Order orderDB = ModelUtils.toOrderDB(order);
			
			boolean newProduct = orderDB.getOrderProductRel().getProduct().getId() == null;			
			Product product = productRepo.save(orderDB.getOrderProductRel().getProduct());
			if(newProduct) {
				orderDB.getOrderProductRel().setProduct(product);
			}
			
			Order updatedOrder = repo.save(orderDB);
			return ModelUtils.toOrderWS(updatedOrder);
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
	
	@Transactional
	public void removeOrder(OrderWS order) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository repo = connector.getOrderRepository();
		graph.beginTx();
		try {
			//TODO: CHECK FOR RELATION REMOVAL
			repo.delete(ModelUtils.toOrderDB(order));
		} catch(Exception e) {
			logger.error("Error",e);
		}
	}
	
	@Transactional
	public OrderWS getOrder(Long id) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		OrderRepository repo = connector.getOrderRepository();
		graph.beginTx();
		try {
			Order c = repo.findOne(id);
			return ModelUtils.toOrderWS(c);
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
}
