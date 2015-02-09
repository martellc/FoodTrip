package com.foodtrip.ftmodelws;

import java.util.Set;

public class ProductWS {

	public enum ProductName {
		TOMATO("Tomato"),
		POTATO("Potato"),
		SALAD("Salad");

		private String name;
		private ProductName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}


	public enum ProductType {
		FRUIT("Fruit"),
		VEGETABLE("Vegetable"),
		MEAT("Meat");

		private String type;
		private ProductType(String type) {
			this.type = type;
		}
		public String getType() {
			return type;
		}
	}

	//general info
	private Long id;
	private String name;
	private String type;
	private String serialNumber;
	private long sawingDate;
	private long harvestDate;

	//production type info
	private boolean biological;
	private boolean sustainable;
	private boolean biodynamic;
	private boolean ipm;
	private Set<String> certifications;

	//food type info
	private boolean ogm;

	//producer info
	private FarmWS farm;

	//spatial information
	private Float lat;
	private Float lng;
	private Float alt;
	
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

	public long getSawingDate() {
		return sawingDate;
	}

	public void setSawingDate(long sawingDate) {
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

	public Set<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(Set<String> certifications) {
		this.certifications = certifications;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isOgm() {
		return ogm;
	}

	public void setOgm(boolean ogm) {
		this.ogm = ogm;
	}

	private String notes;
	public ProductWS() {

	}	

	public ProductWS(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public FarmWS getFarm() {
		return farm;
	}

	public void setFarm(FarmWS farm) {
		this.farm = farm;
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

	public long getHarvestDate() {
		return harvestDate;
	}

	public void setHarvestDate(long harvestDate) {
		this.harvestDate = harvestDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
