package com.foodtrip.ftcontroller;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftcontroller.exception.FoodtripError;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodelws.CompanyWS;

@Stateless
public class FTCompanyController extends FTController {
	private static final Logger logger = Logger.getLogger(FTCompanyController.class);

	@Transactional
	public CompanyWS createCompany(CompanyWS company) throws FoodtripException {
		return updateCompany(company);
	}
	

	@Transactional
	public CompanyWS updateCompany(CompanyWS company) throws FoodtripException {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			Company updatedCompany = repo.save(ModelUtils.toCompanyDB(company));
			return ModelUtils.toCompanyWS(updatedCompany);
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}
		
	}
	
	@Transactional
	public void removeCompany(CompanyWS company) throws FoodtripException {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			repo.delete(ModelUtils.toCompanyDB(company));
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}
	}
	
	@Transactional
	public CompanyWS getCompany(Long id) throws FoodtripException {
		
		if(id == null) {
			logger.error("Null id");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}
		
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			Company c = repo.findOne(id);
			if(c == null) {
				logger.error("Null company");
				throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
			}
			
			return ModelUtils.toCompanyWS(c);
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}
	}


	public CompanyWS[] searchCompany(String vatOrName) {
		
		if((vatOrName == null || vatOrName.isEmpty())) {
			logger.error("Null name or vat");
			return null;
		}
		
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			List<Company> companies = repo.findCompany("(?i).*" + vatOrName + ".*");
			if(companies == null) {
				logger.error("Null companies");
				return null;
			}
			int i = 0;
			CompanyWS[] companiesWS = new CompanyWS[companies.size()];
			for(Company c : companies) {
				companiesWS[i] = ModelUtils.toCompanyWS(c);
				i++;
			}
			return companiesWS;
		} catch(Exception e) {
			logger.error("Error",e);
			return null;
		}
	}
}
