package com.foodtrip.ftmodeldb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class Order {

	public Order() {
		super();
	}

	@GraphId
	private Long id;
	
	//not used
	@Indexed(indexName="serialNumber",indexType=IndexType.FULLTEXT)
	private String serialNumber;
	
	@Fetch @RelatedToVia(type = "REFER_TO", direction = Direction.OUTGOING)
	private OrderProductRel orderProductRel;
	
	//the companies id end point (multiple end point admitted)
	private List<Long> endPoint =new ArrayList<Long>();
	
	private Date date;
	
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

	public OrderProductRel getOrderProductRel() {
		return orderProductRel;
	}

	public void setOrderProductRel(OrderProductRel orderReferT) {
		this.orderProductRel = orderReferT;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Long> getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(List<Long> endPoint) {
		this.endPoint = endPoint;
	}
	
}
