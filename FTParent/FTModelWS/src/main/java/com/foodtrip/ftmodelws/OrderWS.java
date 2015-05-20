package com.foodtrip.ftmodelws;

import java.io.Serializable;


public class OrderWS implements Serializable {

	private static final long serialVersionUID = 1L;

	public OrderWS() {
		super();
	}

	private Long id;
	
	private String serialNumber;
	
	private ProductWS product;
	
	private long date;
	
	private Double amout;

	private Double quantity;
	
	private NewStepWS nextStep;
	
	public Double getAmout() {
		return amout;
	}

	public void setAmout(Double amout) {
		this.amout = amout;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public Long getId() {
		return id;
	}

	public ProductWS getProduct() {
		return product;
	}

	public void setProduct(ProductWS product) {
		this.product = product;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public NewStepWS getNextStep() {
		return nextStep;
	}

	public void setNextStep(NewStepWS nextStep) {
		this.nextStep = nextStep;
	}	
}
