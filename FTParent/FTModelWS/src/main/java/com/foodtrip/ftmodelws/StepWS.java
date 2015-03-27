package com.foodtrip.ftmodelws;

import java.io.Serializable;


public class StepWS  implements Serializable {

	private Long stepID;
	private CompanyWS company;
	
	public StepWS() {
		super();
	}
	
	public CompanyWS getCompany() {
		return company;
	}
	public void setCompany(CompanyWS company) {
		this.company = company;
	}

	public Long getStepID() {
		return stepID;
	}

	public void setStepID(Long stepID) {
		this.stepID = stepID;
	}	
}
