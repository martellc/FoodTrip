package com.foodtrip.ftcontroller.exception;

public enum FoodtripError {
	GENERIC_ERROR ("00"),
    INVALID_ORDER ("01"),
    INVALID_COMPANY("02"), 
    DUPLICATE_STEP("03"),
    INVALID_PRODUCT ("04"),
    INVALID_TRIP ("05"),
    INVALID_STEP ("06"),
    INVALID_STEP_CONFIRM ("07"),
    INVALID_LICENSE("08"),
    INVALID_COMPANY_KEY("09");
    
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
