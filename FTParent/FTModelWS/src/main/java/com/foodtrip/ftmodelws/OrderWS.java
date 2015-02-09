package com.foodtrip.ftmodelws;

import java.util.Date;


public class OrderWS {

	public OrderWS() {
		super();
	}

	private Long id;
	
	private String serialNumber;
	
	private ProductWS product;
	
	private Date date;
	
	private Double amout;

	private Double quantity;
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	
}
