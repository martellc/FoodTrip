package com.foodtrip.ftcontroller;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftmodeldb.Neo4JConnector;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.repo.ProductRepository;

@Stateless
public class FTProductController {
	public static Neo4JConnector connector;
	private static final Logger logger = Logger.getLogger(FTProductController.class);

	@Transactional
	public Product createProduct(Product company) {
		return updateProduct(company);
	}
	

	@Transactional
	public Product updateProduct(Product company) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		graph.beginTx();
		try {
			Product updatedProduct = repo.save(company);
			return updatedProduct;
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
	
	@Transactional
	public void removeProduct(Product company) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		graph.beginTx();
		try {
			repo.delete(company);
		} catch(Exception e) {
			logger.error("Error",e);
		}
	}
	
	@Transactional
	public Product getProduct(Long id) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		graph.beginTx();
		try {
			return repo.findOne(id);
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
}
