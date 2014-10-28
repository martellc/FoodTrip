package com.foodtrip.ftmodeldb.model;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="REFER_TO")
public class OrderProductRel {

	@GraphId 
	private Long id;

	
	public OrderProductRel() {
		
	}
			
	public OrderProductRel(Order order, Product product) {
		super();
		this.order = order;
		this.product = product;
	}

	@StartNode 
	Order order;
	
	@Fetch
	@EndNode 
	Product product;
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	private Double quantity;
	
	private Double amount;
}
