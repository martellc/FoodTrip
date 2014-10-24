package com.foodtrip.ftmodeldb.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

public class Order {

	@GraphId
	private Long id;
	
	@Indexed(indexName="serialNumber",indexType=IndexType.FULLTEXT)
	private String serialNumber;
	
	@Fetch @RelatedToVia(type = "REFER_TO", direction = Direction.OUTGOING)
	private OrderReferToRel orderReferT;
	
	@Fetch @RelatedTo(type="EMITTED_BY", direction=Direction.INCOMING)
	private Farm farm;

	public Long getId() {
		return id;
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

	public OrderReferToRel getOrderReferT() {
		return orderReferT;
	}

	public void setOrderReferT(OrderReferToRel orderReferT) {
		this.orderReferT = orderReferT;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	
}
