package com.foodtrip.ftcontroller.exception;

public class FoodtripException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String businessCode;

	public FoodtripException(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessCode() {
		return businessCode;
	}
}
