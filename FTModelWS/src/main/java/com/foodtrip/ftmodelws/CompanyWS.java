package com.foodtrip.ftmodelws;

import java.util.Collection;

public class CompanyWS {

	private String id;
	private String name;
	private String number;
	
	private PersonWS founder;
	private String type;
	private String vatNumber;
	
	private AddressWS address;
	
	private Collection<Property> properties;
	private Collection<PersonWS> people;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public PersonWS getFounder() {
		return founder;
	}
	public void setFounder(PersonWS founder) {
		this.founder = founder;
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
	public Collection<PersonWS> getPeople() {
		return people;
	}
	public void setPeople(Collection<PersonWS> people) {
		this.people = people;
	}
	public AddressWS getAddress() {
		return address;
	}
	public void setAddress(AddressWS address) {
		this.address = address;
	}
	
	
}
