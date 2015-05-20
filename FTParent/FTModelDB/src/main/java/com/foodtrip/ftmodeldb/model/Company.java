package com.foodtrip.ftmodeldb.model;

import java.util.Date;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Company {

	
	@GraphId 
	private Long id;

	@Indexed(indexName="name", indexType=IndexType.FULLTEXT)
	private String name;

	private String companyID;

	@Indexed(indexName="vatNumber", indexType=IndexType.FULLTEXT)
	private String vatNumber;

	@Indexed(indexName="type", indexType=IndexType.FULLTEXT)
	private String type;

	//spatial information
	private Float lat;
	private Float lng;
	private Float alt;
	
	private String companyTypeDescription;

	private String companyDescription;
	
	private String description;
	
	private String certifications;
	
	private String facebookID;
	private String googlePlusID;
	private String email;
	private Date foundingDate;

	private String country;

	@Indexed(indexName="companyKey", indexType=IndexType.FULLTEXT)
	private String companyKey;
	
	@RelatedTo(type = "CREATED_BY", direction = Direction.INCOMING)
	private Person creator;
	

	@RelatedTo(type = "NOTIFY", direction = Direction.INCOMING)
	private Set<Notification> notifications;
	
	@Fetch 
	@RelatedTo(type = "PRESIDENT_OF", direction = Direction.INCOMING)
	private Person president;

	@Fetch
	@RelatedTo(type = "OWNER_OF", direction = Direction.INCOMING)
	private Person owner;

	@Fetch
	@RelatedTo(type = "WORKS_AT", direction = Direction.INCOMING)
	private Set<Person> employees;

	@Fetch
	@RelatedTo(type = "LOCATED_AT", direction = Direction.OUTGOING)
	private Address address;

	@RelatedTo(type = "DEVICES", direction = Direction.INCOMING)
	private Set<DeviceInfo> devices;

	
	public Set<DeviceInfo> getDevices() {
		return devices;
	}


	public void setDevices(Set<DeviceInfo> devices) {
		this.devices = devices;
	}


	public String getFacebookID() {
		return facebookID;
	}


	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}


	public String getGooglePlusID() {
		return googlePlusID;
	}


	public void setGooglePlusID(String googlePlusID) {
		this.googlePlusID = googlePlusID;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Date getFoundingDate() {
		return foundingDate;
	}


	public void setFoundingDate(Date foundingDate) {
		this.foundingDate = foundingDate;
	}


	public Company() {

	}

	public Company(String name, String companyID, String vatNumber,
			Person president, Person ceo, Address address) {
		super();
		this.name = name;
		this.companyID = companyID;
		this.vatNumber = vatNumber;
		this.president = president;
		this.owner = ceo;
		this.address = address;
	}


	public Company(String name, String companyID, String vatNumber) {
		super();
		this.name = name;
		this.companyID = companyID;
		this.vatNumber = vatNumber;
	}

//	@Fetch @RelatedToVia(type = "PATH", direction = Direction.INCOMING)
//	private Set<CompanyToCompanyRel> companyToCompanyRel = new HashSet<CompanyToCompanyRel>();

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

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public Person getPresident() {
		return president;
	}

	public void setPresident(Person president) {
		this.president = president;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Set<Person> getEmployees() {
		return employees;
	}
	
	public Set<Notification> getNotifications() {
		return notifications;
	}


	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}
	
	public void setEmployees(Set<Person> employees) {
		this.employees = employees;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
//	public Set<CompanyToCompanyRel> getCompanyToCompanyRel() {
//		return companyToCompanyRel;
//	}
//
//	public void setCompanyToCompanyRel(Set<CompanyToCompanyRel> nextStepsRel) {
//		this.companyToCompanyRel = nextStepsRel;
//	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return String.valueOf(this.getId());
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCertifications() {
		return certifications;
	}


	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getCompanyDescription() {
		return companyDescription;
	}


	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}
	
	public Person getCreator() {
		return creator;
	}


	public void setCreator(Person creator) {
		this.creator = creator;
	}

	
	public String getCompanyTypeDescription() {
		return companyTypeDescription;
	}


	public void setCompanyTypeDescription(String companyTypeDescription) {
		this.companyTypeDescription = companyTypeDescription;
	}


	public String getCompanyKey() {
		return companyKey;
	}


	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}


}
