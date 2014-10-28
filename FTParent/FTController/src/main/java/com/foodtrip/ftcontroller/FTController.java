package com.foodtrip.ftcontroller;

import javax.ejb.Stateless;

@Stateless
public class FTController {

	public String addCompany() {
		System.out.println("company added");
		return null;
	}
}
