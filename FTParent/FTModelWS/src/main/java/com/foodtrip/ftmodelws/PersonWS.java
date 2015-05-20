package com.foodtrip.ftmodelws;

import java.io.Serializable;
import java.util.Collection;

public class PersonWS  implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String surname;
	private String fiscalCode;
	private int age;
	private String facebookID;
	private String googlePlusID;
	
	private int gender; //0 M - 1 F
	private String email;
	private String mobileNumber;
	
	private Long birthDate;
	private String birthPlace;
	
	private AddressWS address;
	
	private Collection<Property> properties;

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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	public Collection<Property> getProperties() {
		return properties;
	}

	public void setProperties(Collection<Property> properties) {
		this.properties = properties;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AddressWS getAddress() {
		return address;
	}

	public void setAddress(AddressWS address) {
		this.address = address;
	}

	public String getGooglePlusID() {
		return googlePlusID;
	}

	public void setGooglePlusID(String googlePlusID) {
		this.googlePlusID = googlePlusID;
	}

	public Long getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Long birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}


}
