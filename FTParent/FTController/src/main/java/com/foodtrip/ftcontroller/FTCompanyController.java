package com.foodtrip.ftcontroller;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftmodeldb.Neo4JConnector;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;

@Stateless
public class FTCompanyController {
	public static Neo4JConnector connector;
	private static final Logger logger = Logger.getLogger(FTCompanyController.class);

	@Transactional
	public Company createCompany(Company company) {
		return updateCompany(company);
	}
	

	@Transactional
	public Company updateCompany(Company company) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			Company updatedCompany = repo.save(company);
			return updatedCompany;
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
	
	@Transactional
	public void removeCompany(Company company) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			repo.delete(company);
		} catch(Exception e) {
			logger.error("Error",e);
		}
	}
	
	@Transactional
	public Company getCompany(Long id) {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			return repo.findOne(id);
		} catch(Exception e) {
			logger.error("Error",e);
		}
		
		return null;
	}
}
