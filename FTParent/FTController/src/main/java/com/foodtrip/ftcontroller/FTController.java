package com.foodtrip.ftcontroller;

import javax.ejb.Stateless;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftmodeldb.Neo4JConnector;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;

@Stateless
public class FTController {

	public static Neo4JConnector connector;
	
	@Autowired CompanyRepository companyRepository2;

	
	@Transactional
	public String saveCompany() {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository companyRepository = connector.getCompanyRepository();
		graph.beginTx();
		try {
			companyRepository.deleteAll();
		}catch(Exception e) {

		}

		Company c1 = new Company("Company 1","1", "11111");
		companyRepository.save(c1);

		System.out.println("company added");
		return null;
	}
}
