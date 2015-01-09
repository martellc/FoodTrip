package com.foodtrip.ftmodeldb.model;

import java.util.Date;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="PATH")
public class CompanyToCompanyRel {
	
	public CompanyToCompanyRel() {
		
	}

	@GraphId 
	private Long id;

	private Long originalOrderID;
	
	private String originalOrderSerialNumber;
	
	@Fetch @StartNode
	private Company start;
	
	@Fetch @EndNode
	private Company end;
	
	private Double quantity;
	
	private Double amount;

	//spatial information
	private Float lat;
	private Float lng;
	private Float alt;
	
	//date
	private Date date;
	
	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Float getAlt() {
		return alt;
	}

	public void setAlt(Float alt) {
		this.alt = alt;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getOriginalOrderID() {
		return originalOrderID;
	}

	public void setOriginalOrderID(Long originalOrderID) {
		this.originalOrderID = originalOrderID;
	}

	public String getOriginalOrderSerialNumber() {
		return originalOrderSerialNumber;
	}

	public void setOriginalOrderSerialNumber(String originalOrderSerialNumber) {
		this.originalOrderSerialNumber = originalOrderSerialNumber;
	}

	public Company getStart() {
		return start;
	}

	public void setStart(Company seller) {
		this.start = seller;
	}

	public Company getEnd() {
		return end;
	}

	public void setEnd(Company buyer) {
		this.end = buyer;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
