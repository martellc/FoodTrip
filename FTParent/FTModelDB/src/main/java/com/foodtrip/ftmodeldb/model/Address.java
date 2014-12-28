package com.foodtrip.ftmodeldb.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Address {

	@GraphId 
	private Long id;

	public Address() {
		super();
	}

	private String streetName;
	
	private String streetNumber;
	
	private String zipCode;
	
	@RelatedTo(type = "RESIDE_ON", direction = Direction.OUTGOING)
	private City city;

	private String state;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String street) {
		this.streetName = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String number) {
		this.streetNumber = number;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
