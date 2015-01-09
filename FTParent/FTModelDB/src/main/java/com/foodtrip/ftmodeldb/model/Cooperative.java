package com.foodtrip.ftmodeldb.model;

import java.util.List;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Cooperative extends Company {
	

	@Fetch
	@RelatedTo(type = "PRODUCE_FOR", direction = Direction.INCOMING)
	private List<Farm> farmers;
	
	public Cooperative() {
		
	}
			
	public Cooperative(String name, String companyID, String vatNumber,
			Person president, Person ceo, Address address) {
		super(name,companyID,vatNumber,president,ceo,address);
	}


	public Cooperative(String name, String companyID, String vatNumber) {
		super(name,companyID,vatNumber);
	}

	public List<Farm> getFarmers() {
		return farmers;
	}

	public void setFarmers(List<Farm> farmers) {
		this.farmers = farmers;
	}
}
