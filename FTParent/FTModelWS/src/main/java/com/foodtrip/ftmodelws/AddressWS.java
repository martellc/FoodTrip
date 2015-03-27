package com.foodtrip.ftmodelws;

import java.io.Serializable;

public class AddressWS  implements Serializable {

	private Long id;
	private String streetName;
	private String streetNumber;
	private CityWS city;
	private String zipCode;
	private String state;
	
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public CityWS getCity() {
		return city;
	}
	public void setCity(CityWS city) {
		this.city = city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
}
