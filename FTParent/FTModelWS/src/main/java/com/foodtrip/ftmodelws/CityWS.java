package com.foodtrip.ftmodelws;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CityWS {

	public CityWS() {
		
	}
	
	public CityWS(String name, Long id) {
		this.name = name;
		this.id = id;
	}
	
	private String name;
	private Long id;
	
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
}
