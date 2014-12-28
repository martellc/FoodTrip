package com.foodtrip.ftmodeldb.model;

import java.util.List;

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

	@Indexed(numeric=true)
	private int harvestDate;

	private int sawingDate;
	
	//production type info
	private boolean bilogical;
	private boolean sustainable;
	private boolean biodynamic;
	private boolean ipm;
	private List<String> certifications;

	//food type info
	private boolean ogm;


	@RelatedTo(type = "PRODUCED_BY", direction = Direction.OUTGOING)
	private Farm farm;

	public Product() {

	}	

	public Product(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public int getSawingDate() {
		return sawingDate;
	}

	public void setSawingDate(int sawingDate) {
		this.sawingDate = sawingDate;
	}

	public boolean isBilogical() {
		return bilogical;
	}

	public void setBilogical(boolean bilogical) {
		this.bilogical = bilogical;
	}

	public boolean isSustainable() {
		return sustainable;
	}

	public void setSustainable(boolean sustainable) {
		this.sustainable = sustainable;
	}

	public boolean isBiodynamic() {
		return biodynamic;
	}

	public void setBiodynamic(boolean biodynamic) {
		this.biodynamic = biodynamic;
	}

	public boolean isIpm() {
		return ipm;
	}

	public void setIpm(boolean ipm) {
		this.ipm = ipm;
	}

	public List<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}

	public boolean isOgm() {
		return ogm;
	}

	public void setOgm(boolean ogm) {
		this.ogm = ogm;
	}

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
