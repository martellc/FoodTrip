package com.foodtrip.ftmodelws;

import java.util.Collection;


public class CompanyWS {

	private Long id;
	private String name;
	private String number;
	private String type;
	private String vatNumber;
	
	
	private PersonWS owner;
	private PersonWS president;
	private AddressWS address;
	
	private Collection<Property> properties;
	private Collection<PersonWS> employees;
	
	//spatial information
	private Float lat;
	private Float lng;
	private Float alt;
	
	public CompanyWS() {
		
	}
		
	public CompanyWS(Long id, String name, String number, String type) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.type = type;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Collection<Property> getProperties() {
		return properties;
	}
	public void setProperties(Collection<Property> properties) {
		this.properties = properties;
	}
	public PersonWS getOwner() {
		return owner;
	}
	public void setOwner(PersonWS founder) {
		this.owner = founder;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVatNumber() {
		return vatNumber;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	public Collection<PersonWS> getEmployees() {
		return employees;
	}
	public void setEmployees(Collection<PersonWS> employees) {
		this.employees = employees;
	}
	public AddressWS getAddress() {
		return address;
	}
	public void setAddress(AddressWS address) {
		this.address = address;
	}

	public PersonWS getPresident() {
		return president;
	}

	public void setPresident(PersonWS president) {
		this.president = president;
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
