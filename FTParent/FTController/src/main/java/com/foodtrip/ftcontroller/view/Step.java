package com.foodtrip.ftcontroller.view;

import java.sql.Timestamp;

import com.foodtrip.ftmodeldb.model.Company;

public class Step implements Comparable<Step> {

	public Step() {
		
	}
	
	public Step(Long id,Company company, Float lat, Float lng, Float alt,
			Timestamp date, Double quantity, Double amount) {
		super();
		this.id = id;
		this.company = company;
		this.lat = lat;
		this.lng = lng;
		this.alt = alt;
		this.date = date;
		this.quantity = quantity;
		this.amount = amount;
	}

	private Long id;

	private Double quantity;
	
	private Double amount;

	//spatial information
	private Float lat;
	private Float lng;
	private Float alt;
	
	//date
	private Timestamp date;

	private Company company;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public boolean equals(Object obj ) {
		
		if (!(obj instanceof Step)) {
			return false;
		}
		Step otherStep = (Step) obj;
		if(this.getId() == null && otherStep.getId()!= null) {
			return false;
		}
		
		if(this.getId().equals(otherStep.getId())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.getId()!= null ? this.getId().hashCode() : 0 ;
	}

	public int compareTo(Step o) {
		return this.getId().compareTo(o.getId());
	}
	
}
