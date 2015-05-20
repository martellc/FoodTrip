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
	
	//spatial info
	private Float alt;
	public Float getAlt() {
		return alt;
	}

	public void setAlt(Float alt) {
		this.alt = alt;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	private Float lng;
	private Float lat;
	
	@RelatedTo(type = "RESIDE_ON", direction = Direction.OUTGOING)
	private City city;

	private String cityName;
	
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
