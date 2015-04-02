package com.foodtrip.ftmodelws;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodStepWS implements Comparable<FoodStepWS>,Serializable {

	public static final String MARKER_ICON_START="./img/start.png";
	public static final String MARKER_ICON="./img/marker.png";
	public static final String MARKER_ICON_END="./img/end.png";
	
	public FoodStepWS() {
		
	}
	

	private Long id;

	private Double quantity;
	private Double amount;

	//spatial information
	private Float lat;
	private Float lng;
	private Float alt;
	
	private Long parentID;
	
	//date
	private Date date;

	private CompanyWS company;

	private Map<Long,FoodStepWS> children = new HashMap<Long, FoodStepWS>();
	
	private String markerIcon=MARKER_ICON;

	private String infoWindow;
	
	public FoodStepWS(Long id,CompanyWS company, Float lat, Float lng, Float alt,
			Date date, Double quantity, Double amount,String markerIcon,String infoWindow) {
		super();
		this.id = id;
		this.company = company;
		this.lat = lat;
		this.lng = lng;
		this.alt = alt;
		this.date = date;
		this.quantity = quantity;
		this.amount = amount;
		this.markerIcon = markerIcon;
		this.infoWindow = infoWindow;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Float getLat() {
		return lat/10000000;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng/10000000;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public CompanyWS getCompany() {
		return company;
	}

	public void setCompany(CompanyWS company) {
		this.company = company;
	}
	
	@Override
	public boolean equals(Object obj ) {
		
		if (!(obj instanceof FoodStepWS)) {
			return false;
		}
		FoodStepWS otherStep = (FoodStepWS) obj;
		if(this.getId() == null && otherStep.getId()!= null) {
			return false;
		}
		
		if(!this.getId().equals(otherStep.getId())) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.getId()!= null ? this.getId().hashCode() : 0 ;
	}

	public int compareTo(FoodStepWS o) {
		return this.getId().compareTo(o.getId());
	}

	public String getMarkerIcon() {
		return markerIcon;
	}

	public void setMarkerIcon(String markerIcon) {
		this.markerIcon = markerIcon;
	}

	public String getInfoWindow() {
		return infoWindow;
	}

	public void setInfoWindow(String infoWindow) {
		this.infoWindow = infoWindow;
	}

	public Map<Long,FoodStepWS> getChildren() {
		return children;
	}

	public void setChildren(Map<Long,FoodStepWS> children) {
		this.children = children;
	}

	public FoodStepWS[] getChildrenArray() {
		Collection<FoodStepWS> c = this.children.values();
		return c.toArray(new FoodStepWS[c.size()]);
	}

	public Long getParentID() {
		return parentID;
	}

	public void setParentID(Long parentID) {
		this.parentID = parentID;
	}
	
	public String toString() {
		return this.getId() + "[Company:" + company.getId() + "]"; 
	}
}
