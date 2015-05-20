package com.foodtrip.ftmodelws;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ProductWS  implements Serializable {

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

	private String description;
	
	//production type info
	private boolean biological;
	private boolean sustainable;
	private boolean biodynamic;
	private boolean ipm;
	private String certifications;

	//food type info
	private boolean ogm;

	//producer info
	private CompanyWS farm;

	//spatial information
	private Float lat;
	private Float lng;
	private Float alt;
	
	private List<OrderWS> orders;
	
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

	public String getCertifications() {
		return certifications;
	}

	public void setCertifications(String certifications) {
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

	public CompanyWS getFarm() {
		return farm;
	}

	public void setFarm(CompanyWS farm) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductWS other = (ProductWS) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<OrderWS> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderWS> orders) {
		this.orders = orders;
	}
	
	
}
