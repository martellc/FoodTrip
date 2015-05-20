package com.foodtrip.ftcontroller;

import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.transaction.annotation.Transactional;

import com.foodtrip.ftcontroller.exception.FoodtripError;
import com.foodtrip.ftcontroller.exception.FoodtripException;
import com.foodtrip.ftmodeldb.model.Company;
import com.foodtrip.ftmodeldb.model.DeviceInfo;
import com.foodtrip.ftmodeldb.model.Notification;
import com.foodtrip.ftmodeldb.model.Product;
import com.foodtrip.ftmodeldb.repo.CompanyRepository;
import com.foodtrip.ftmodeldb.repo.NotificationRepository;
import com.foodtrip.ftmodeldb.repo.ProductRepository;
import com.foodtrip.ftmodelws.CompanyWS;
import com.foodtrip.ftmodelws.NotificationWS;

@Stateless
public class FTCompanyController extends FTController {
	private static final Logger logger = Logger.getLogger(FTCompanyController.class);
	private static final long ONEYEAR = 365;

	public void checkCompany(CompanyWS company) throws FoodtripException {
		if(company.getCompanyKey() == null) {
			throw new FoodtripException(FoodtripError.INVALID_COMPANY_KEY.getCode());
		}
		
		CompanyRepository repo = connector.getCompanyRepository();
		Company c = repo.findOne(company.getId());
		if(c == null) {
			throw new FoodtripException(FoodtripError.INVALID_COMPANY_KEY.getCode());
		}
		
		if(!c.getCompanyKey().equals(company.getCompanyKey())) {
			throw new FoodtripException(FoodtripError.INVALID_COMPANY_KEY.getCode());			
		}
	}
	
	
	@Transactional
	public CompanyWS createCompany(CompanyWS company) throws FoodtripException {
		return updateCompany(company);
	}
	
	@Transactional
	public boolean isLicenseOK(Long companyID, Long uuid){
		CompanyRepository repo = connector.getCompanyRepository();
		if(companyID == null || uuid == null) {
			return false;
		}
		
		try {
			DeviceInfo d = repo.getDeviceInfo(companyID, uuid);
			long today = new Date().getTime();
			long diff = today - d.getDate();
		    
			long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			if(days > ONEYEAR) {
				return false;
			}
		} catch(Exception e) {
			logger.error("Error",e);
			return false;
		}
		
		return true;
	}

	@Transactional
	public CompanyWS updateCompany(CompanyWS company) throws FoodtripException {
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		
		if(company == null) {
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}
		
		boolean isCreation = false;
		byte[] aesKey = new byte[16]; // 16 bytes = 128 bits
		if(company.getId() != null) {
			checkCompany(company);
		} else {
			//creation time
			Random ranGen = new SecureRandom();
			ranGen.nextBytes(aesKey);
			String random = String.valueOf(aesKey);
			company.setCompanyKey(random);
			isCreation = true;
		}
		
		Company updatedCompany;
		Transaction tx = graph.beginTx();
		try {
			updatedCompany = repo.save(ModelUtils.toCompanyDB(company));
			tx.success();
		} catch(Exception e) {
			tx.failure();
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		} finally {
			tx.close();
		}
		
		CompanyWS ret = ModelUtils.toCompanyWS(updatedCompany);
		if(isCreation) {
			ret.setCompanyKey(String.valueOf(aesKey));
		}
		return ret;
	}
	
	@Transactional
	public void removeCompany(CompanyWS company) throws FoodtripException {
		
		checkCompany(company);
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
	public CompanyWS getCompanyByKey(String companyKey) throws FoodtripException {
		if(companyKey == null || "".equals(companyKey)) {
			logger.error("Null uuid");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}
		GraphDatabaseService graph = connector.graphDatabaseService();
		CompanyRepository repo = connector.getCompanyRepository();
		graph.beginTx();
		try {
			Company c = repo.findByCompanyKey(companyKey);
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
			vatOrName = URLDecoder.decode(vatOrName,"UTF-8");
			
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
	
	public NotificationWS[] getNotifications(Long company) throws FoodtripException {
		
		if((company == null || company== 0)) {
			logger.error("Null name or vat");
			throw new FoodtripException(FoodtripError.INVALID_COMPANY.getCode());
		}
		
		GraphDatabaseService graph = connector.graphDatabaseService();
		NotificationRepository repo = connector.getNotificationRepository();
		CompanyRepository repoCompany = connector.getCompanyRepository();
		ProductRepository repoProduct = connector.getProductRepository();
		
		graph.beginTx();
		
		try {
			List<Notification> notifications = repo.getNotifications(company);
			if( notifications == null) {
				return new NotificationWS[0];
			}
			
			int i = 0;
			NotificationWS[] notificationsWS = new NotificationWS[notifications.size()];
			for(Notification n : notifications) {
				
				Product p = repoProduct.findOne(n.getProductID());
				if(p == null || p.getId() == 0) {
					continue;
				}
				
				Company c = repoCompany.findOne(n.getCompanyID());
				if(c == null || c.getId() == 0) {
					continue;
				}
				
				notificationsWS[i] = ModelUtils.toNotificationWS(n,p,c);
				i++;
			}
			return notificationsWS;
		} catch(Exception e) {
			logger.error("Error",e);
			throw new FoodtripException(FoodtripError.GENERIC_ERROR.getCode());
		}
	}
}
