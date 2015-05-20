package com.foodtrip.ftmodeldb.model;

import java.util.Date;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Notification {

	public static final int STATE_STEP_INVALID = -2;
	public static final int STATE_STEP_CONFIRMED = 0;
	public static final int STATE_NEW = 1;
	
	@GraphId 
	private Long id;
	
	private Long companyID;
	private Long stepID;
	
	private long date = new Date().getTime();
	
	private Long foodTrip;
	private String message;
	
	private int state;
	
	private Long productID;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompanyID() {
		return companyID;
	}
	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}
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
	
	public Long getProductID() {
		return productID;
	}
	public void setProductID(Long productID) {
		this.productID = productID;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
