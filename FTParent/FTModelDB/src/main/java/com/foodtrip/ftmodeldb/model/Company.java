package com.foodtrip.ftmodeldb.model;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Company {

	
	@GraphId 
	private Long id;

	@Indexed(indexName="name", indexType=IndexType.FULLTEXT)
	private String name;

	private String companyID;

	private String vatNumber;

	private String type;

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

	@Fetch @RelatedToVia(type = "PATH", direction = Direction.INCOMING)
	private Set<CompanyToCompanyRel> companyToCompanyRel = new HashSet<CompanyToCompanyRel>();

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
	public Set<CompanyToCompanyRel> getCompanyToCompanyRel() {
		return companyToCompanyRel;
	}

	public void setCompanyToCompanyRel(Set<CompanyToCompanyRel> nextStepsRel) {
		this.companyToCompanyRel = nextStepsRel;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
}
