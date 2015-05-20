package com.foodtrip.ftcontroller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftcontroller.exception.FoodtripError;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.ProductRepository;
import com.foodtrip.ftmodelws.ProductWS;

@Stateless
public class FTProductController extends FTController {
	
	static final Logger logger = Logger.getLogger(FTProductController.class);

	@Transactional
	public ProductWS createProduct(ProductWS pWS) throws FoodtripException {
		return updateProduct(pWS);
	}
	

	@Transactional
	public ProductWS updateProduct(ProductWS pWS) throws FoodtripException {
		if(pWS == null) {
			logger.error("Farm is null");
			throw new FoodtripException(FoodtripError.INVALID_PRODUCT.getCode());

		}
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		CompanyRepository companyRepo = connector.getCompanyRepository();
		
		if(pWS.getFarm() == null) {
			logger.error("Farm is null");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());

		}
		new FTCompanyController().checkCompany(pWS.getFarm());
		
		graph.beginTx();
		try {
			Company f = companyRepo.findOne(pWS.getFarm().getId());
			if (f == null ) {
				logger.error("Invalid farm.");
				throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
			}
			Product p = ModelUtils.toProductDB(pWS);
			p.setFarm(f);
			Product updatedProduct = repo.save(p);
			return ModelUtils.toProductWS(updatedProduct);
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());

		} finally {
			
		}
	}
	
	@Transactional
	public void removeProduct(ProductWS pWS) throws FoodtripException {
		if(pWS == null) {
			logger.error("Farm is null");
			throw new FoodtripException(FoodtripError.INVALID_PRODUCT.getCode());

		}
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		graph.beginTx();
		try {
			repo.delete(ModelUtils.toProductDB(pWS));
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}finally{}
	}
	
	@Transactional
	public ProductWS getProduct(Long id) throws FoodtripException {
		if(id == null) {
			logger.error("is is null");
			throw new FoodtripException(FoodtripError.INVALID_PRODUCT.getCode());

		}
		
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		graph.beginTx();
		try {
			Product p = repo.findOne(id);
			return ModelUtils.toProductWS(p);
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}finally{}
	}


	public List<ProductWS> getFarmersProducts(Long farmerId) throws FoodtripException {
		if(farmerId == null) {
			logger.error("id is null");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}
		
		GraphDatabaseService graph = connector.graphDatabaseService();
		ProductRepository repo = connector.getProductRepository();
		List<ProductWS> productsWSList = new ArrayList<ProductWS>();
		graph.beginTx();
		try {
			Iterable<Product>  products = repo.getProducts(farmerId);
			if (products == null) {
				return productsWSList;
			}
			
			Iterator<Product>productsIt = products.iterator();
		
			while (productsIt.hasNext()) {
				productsWSList.add(ModelUtils.toProductWS(productsIt.next()));
			}
			
			return productsWSList;
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}finally{}
	}
}