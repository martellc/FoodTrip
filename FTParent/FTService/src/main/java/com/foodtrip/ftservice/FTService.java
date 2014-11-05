package com.foodtrip.ftservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.foodtrip.ftcontroller.FTController;
import com.foodtrip.ftmodelws.CompanyWS;

@WebService(
		targetNamespace = "http://com.foodtrip.ftservice",
		name = "ftservice",
		serviceName = "ftservice"
		)
public class FTService {

	@WebMethod
	public String addCompany(CompanyWS company) {
		return new FTController().saveCompany();
	}
	
	@WebMethod
	public String getUniqueID() {
		return null;
	}
}
