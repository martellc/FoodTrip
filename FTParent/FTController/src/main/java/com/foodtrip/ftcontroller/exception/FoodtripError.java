package com.foodtrip.ftcontroller.exception;

public enum FoodtripError {
	GENERIC_ERROR ("00"),
    INVALID_ORDER ("01"),
    INVALID_COMPANY("02"), 
    DUPLICATE_STEP("03"),
    INVALID_PRODUCT ("04"),
    INVALID_TRIP ("05");
    
    private String code;
    FoodtripError(String code) {
        this.setCode(code);
    }
	public String getCode() {
		return code;
	}
	
	private void setCode(String code) {
		this.code = code;
	}
}
