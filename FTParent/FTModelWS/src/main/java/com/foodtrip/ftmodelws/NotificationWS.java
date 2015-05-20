package com.foodtrip.ftmodelws;

import java.io.Serializable;
import java.util.Date;

public class NotificationWS implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private CompanyWS company;
	
	private Long stepID;
	
	private long date = new Date().getTime();
	
	private Long foodTrip;
	
	private String message;
	
	private int state;
	
	private ProductWS product;

	public Long getStepID() {
		return stepID;
	}
	public void setStepID(Long stepID) {
		this.stepID = stepID;
	}
	
	public Long getFoodTrip() {
		return foodTrip;
	}
	public void setFoodTrip(Long foodTrip) {
		this.foodTrip = foodTrip;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public ProductWS getProduct() {
		return product;
	}
	public void setProduct(ProductWS product) {
		this.product = product;
	}
	public CompanyWS getCompany() {
		return company;
	}
	public void setCompany(CompanyWS company) {
		this.company = company;
	}
	
	public boolean isNew() {
		return state == 1;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
