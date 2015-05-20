package com.foodtrip.ftmodeldb.model;

import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
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
	private boolean biological;
	private boolean sustainable;
	private boolean biodynamic;
	private boolean ipm;
	private String certifications;

	//food type info
	private boolean ogm;

	//spatial information
	private Float lat;
	private Float lng;
	private Float alt;
	
	private String description;
	
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

	@Fetch
	@RelatedTo(type = "PRODUCED_BY", direction = Direction.OUTGOING)
	private Company farm;

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

	public boolean isBiological() {
		return biological;
	}

	public void setBiological(boolean bilogical) {
		this.biological = bilogical;
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

	public String getCertifications() {
		return certifications;
	}

	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}

	public boolean isOgm() {
		return ogm;
	}

	public void setOgm(boolean ogm) {
		this.ogm = ogm;
	}

	public Company getFarm() {
		return farm;
	}

	public void setFarm(Company farm) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
