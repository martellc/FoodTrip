package com.foodtrip.ftmodelws;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StepWS {

	private Long endPoint;
	private Long orderId;
	private CompanyWS company;
	
	public StepWS() {
		super();
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public CompanyWS getCompany() {
		return company;
	}
	public void setCompany(CompanyWS company) {
		this.company = company;
	}

	public Long getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Long endPoint) {
		this.endPoint = endPoint;
	}
	
}
