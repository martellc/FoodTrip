package com.foodtrip.ftmodelws;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TripIDWS {

	private Long id;
	private Long endCompany;
	
	public TripIDWS() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEndCompany() {
		return endCompany;
	}

	public void setEndCompany(Long endCompany) {
		this.endCompany = endCompany;
	}
}
