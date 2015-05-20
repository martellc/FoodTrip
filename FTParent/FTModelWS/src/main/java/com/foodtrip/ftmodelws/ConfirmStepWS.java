package com.foodtrip.ftmodelws;

import java.io.Serializable;


public class ConfirmStepWS  implements Serializable {

	private static final long serialVersionUID = 1L;
		
	private Long notificationID;
	
	private CompanyWS company;
	
	private boolean confirmed = true;
	
	public ConfirmStepWS() {
		super();
	}
	

	public CompanyWS getCompany() {
		return company;
	}
	public void setCompany(CompanyWS company) {
		this.company = company;
	}

	public Long getNotificationID() {
		return notificationID;
	}


	public void setNotificationID(Long notificationID) {
		this.notificationID = notificationID;
	}


	public boolean isConfirmed() {
		return confirmed;
	}


	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
}
