package com.foodtrip.ftmodelws;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TripIdWS {

	private Long id;
	private Long endCompany;
	
	public TripIdWS() {
	
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
