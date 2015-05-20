package com.foodtrip.ftmodelws;

import java.io.Serializable;


public class NewStepWS  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long endPoint;
	private Long orderId;
	private CompanyWS company;
	
	private PointWS point;
	
	public NewStepWS() {
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

	public PointWS getPoint() {
		return point;
	}

	public void setPoint(PointWS point) {
		this.point = point;
	}
	
}
