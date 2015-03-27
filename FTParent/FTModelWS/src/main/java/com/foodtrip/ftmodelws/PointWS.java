package com.foodtrip.ftmodelws;

import java.io.Serializable;

public class PointWS implements Serializable{

	private Float lng;
	private Float lat;
	private Float alt;
	
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getAlt() {
		return alt;
	}
	public void setAlt(Float alt) {
		this.alt = alt;
	}
	public Float getLng() {
		return lng;
	}
	public void setLng(Float lng) {
		this.lng = lng;
	}
	
}
