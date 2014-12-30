package com.foodtrip.ftcontroller;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftmodeldb.Neo4JConnector;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.repo.ProductRepository;
import com.foodtrip.ftmodelws.ProductWS;

@Stateless
public class FTProductController {
	public static Neo4JConnector connector;
	private static final Logger logger = Logger.getLogger(FTProductController.class);

	@Transactional
	public ProductWS createProduct(ProductWS pWS) {
		return updateProduct(pWS);
	}
	

	@Transactional
	public ProductWS updateProduct(ProductWS pWS) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		graph.beginTx();
		try {
			Product updatedProduct = repo.save(ModelUtils.toProductDB(pWS));
			return ModelUtils.toProductWS(updatedProduct);
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
	
	@Transactional
	public void removeProduct(ProductWS pWS) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		graph.beginTx();
		try {
			repo.delete(ModelUtils.toProductDB(pWS));
		} catch(Exception e) {
			logger.error("Error",e);
		}
	}
	
	@Transactional
	public ProductWS getProduct(Long id) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		graph.beginTx();
		try {
			Product p = repo.findOne(id);
			return ModelUtils.toProductWS(p);
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
}