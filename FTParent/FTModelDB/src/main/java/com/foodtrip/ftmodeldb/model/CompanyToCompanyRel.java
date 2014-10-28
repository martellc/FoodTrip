package com.foodtrip.ftmodeldb.model;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="NEXT")
public class CompanyToCompanyRel {
	
	public CompanyToCompanyRel() {
		
	}
	@GraphId 
	private Long id;

	private Long originalOrderID;
	
	private String originalOrderSerialNumber;
	
	@Fetch @StartNode
	private Company seller;
	
	@EndNode
	private Company buyer;
	
	private Double quantity;
	
	private Double amount;

	public Long getOriginalOrderID() {
		return originalOrderID;
	}

	public void setOriginalOrderID(Long originalOrderID) {
		this.originalOrderID = originalOrderID;
	}

	public String getOriginalOrderSerialNumber() {
		return originalOrderSerialNumber;
	}

	public void setOriginalOrderSerialNumber(String originalOrderSerialNumber) {
		this.originalOrderSerialNumber = originalOrderSerialNumber;
	}

	public Company getSeller() {
		return seller;
	}

	public void setSeller(Company seller) {
		this.seller = seller;
	}

	public Company getBuyer() {
		return buyer;
	}

	public void setBuyer(Company buyer) {
		this.buyer = buyer;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}