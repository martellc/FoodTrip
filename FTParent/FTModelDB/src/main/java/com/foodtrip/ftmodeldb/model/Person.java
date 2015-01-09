package com.foodtrip.ftmodeldb.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Person {
	@GraphId 
	private Long id;

	private String name;

	@Indexed(indexName="personSurname", indexType=IndexType.FULLTEXT)
	private String surname;

	private String personID;
	
	private String email;
	
	private String facebookAccoount;
	
	private String googlePlusAccount;
	
	private int age;

	@Fetch
	@RelatedTo(type="LIVE_IN", direction=Direction.OUTGOING)
	private Address address;
    
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	private String mobileNumber;
	
	private boolean verifiedUser;
	
	private String fiscalCode;

	public Person() {
		
	}
	
	public Person(Long id,String name, String surname) {
		setId(id);
		setName(name);
		setSurname(surname);
	}
	
	public Person(String name, String surname) {
		setName(name);
		setSurname(surname);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookAccoount() {
		return facebookAccoount;
	}

	public void setFacebookAccoount(String facebookAccoount) {
		this.facebookAccoount = facebookAccoount;
	}

	public String getGooglePlusAccount() {
		return googlePlusAccount;
	}

	public void setGooglePlusAccount(String googlePlusAccount) {
		this.googlePlusAccount = googlePlusAccount;
	}

	public boolean isVerifiedUser() {
		return verifiedUser;
	}

	public void setVerifiedUser(boolean verifiedUser) {
		this.verifiedUser = verifiedUser;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
}
