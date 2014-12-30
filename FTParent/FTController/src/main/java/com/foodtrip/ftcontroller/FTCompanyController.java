package com.foodtrip.ftcontroller;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftmodeldb.Neo4JConnector;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.model.Farm;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.FarmRepository;
import com.foodtrip.ftmodelws.CompanyWS;
import com.foodtrip.ftmodelws.FarmWS;

@Stateless
public class FTCompanyController {
	public static Neo4JConnector connector;
	private static final Logger logger = Logger.getLogger(FTCompanyController.class);

	@Transactional
	public CompanyWS createCompany(CompanyWS company) {
		return updateCompany(company);
	}
	

	@Transactional
	public CompanyWS updateCompany(CompanyWS company) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			Company updatedCompany = repo.save(ModelUtils.toCompanyDB(company));
			return ModelUtils.toCompanyWS(updatedCompany);
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
	
	@Transactional
	public void removeCompany(CompanyWS company) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			repo.delete(ModelUtils.toCompanyDB(company));
		} catch(Exception e) {
			logger.error("Error",e);
		}
	}
	
	@Transactional
	public CompanyWS getCompany(Long id) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			Company c = repo.findOne(id);
			return ModelUtils.toCompanyWS(c);
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}


	public FarmWS updateFarm(FarmWS farm) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		FarmRepository repo = connector.getFarmRepository();
		graph.beginTx();
		try {
			Farm f = repo.save(ModelUtils.toFarmDB(farm));
			return ModelUtils.toFarmWS(f);
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
}
