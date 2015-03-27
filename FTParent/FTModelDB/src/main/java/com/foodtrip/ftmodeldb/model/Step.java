package com.foodtrip.ftmodeldb.model;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;


@NodeEntity
public class Step {

	public static enum Rels implements RelationshipType
	{
		STEP
	}
	
	public Step() {
		super();
	}

	@GraphId
	private Long id;

	private Long companyID;

	private Long orderID;

	private long date;

	private Double quantity;

	//spatial information
	private Float lat;
	private Float lng;
	private Float alt;


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

	private Double amount;

	@Fetch @RelatedTo(type = "STEP", direction = Direction.OUTGOING)
	private Set<Step> nextSteps = new HashSet<Step>();
	
	public Long getId() {
		return id;
	}

	public Set<Step> getNextSteps() {
		return nextSteps;
	}

	public void setNextSteps(Set<Step> nextSteps) {
		this.nextSteps = nextSteps;
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

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
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


}
