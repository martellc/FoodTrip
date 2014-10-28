package com.foodtrip.ftmodeldb.model;

import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Farm extends Company {
	public Farm() {
		
	}
			
	public Farm(String name, String companyID, String vatNumber,
			Person president, Person ceo, Address address) {
		super(name,companyID,vatNumber,president,ceo,address);
	}


	public Farm(String name, String companyID, String vatNumber) {
		super(name,companyID,vatNumber);
	}
}
