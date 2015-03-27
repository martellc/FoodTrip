package com.foodtrip.ftmodelws;

import java.io.Serializable;



public class TripIDWS  implements Serializable {

	private String id;

	public TripIDWS() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
