package com.foodtrip.ftmodeldb.model;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity
public class NextStepRel {

	private Long originalOrderID;
	
	private String originalOrderSerialNumber;
	
	@StartNode
	private String seller;
	
	@EndNode
	private String buyer;
	
	private String quantity;
	
	private String amount;

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

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
