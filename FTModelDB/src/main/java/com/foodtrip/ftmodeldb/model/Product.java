package com.foodtrip.ftmodeldb.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Product {

	@GraphId 
	private Long id;
	
	@Indexed(indexType=IndexType.FULLTEXT, indexName="productName") 
	private String name;
	
	@Indexed(indexType=IndexType.FULLTEXT, indexName="productType")
	private String type;
	
	private String serialNumber;
	
	@Indexed(numeric=true, indexName="productType")
	private int harvestDate;

	@RelatedTo(type = "PRODUCED_BY", direction = Direction.OUTGOING)
	private Farm farm;
	
	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getHarvestDate() {
		return harvestDate;
	}

	public void setHarvestDate(int harvestDate) {
		this.harvestDate = harvestDate;
	}
}
