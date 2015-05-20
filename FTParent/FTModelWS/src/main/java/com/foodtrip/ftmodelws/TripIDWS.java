package com.foodtrip.ftmodelws;

import java.io.Serializable;



public class TripIDWS  implements Serializable {

	private static final long serialVersionUID = 1L;
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
