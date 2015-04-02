package com.foodtrip.ftmodeldb.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Notification {

	@GraphId 
	private Long id;
	
	private String companyName;
	private String companyVatNumber;
	private String companyAddress;
	
	private String date;
	private Long foodTrip;
	private String message;
	
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyVatNumber() {
		return companyVatNumber;
	}
	public void setCompanyVatNumber(String companyVatNumber) {
		this.companyVatNumber = companyVatNumber;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
