package com.foodtrip.ftmodeldb.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
@TypeAlias("Order")
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
	
	private long date;
	
	@Fetch @RelatedTo(type = "FIRST_STEP", direction = Direction.OUTGOING)
	private Step step = new Step();
	
	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

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

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}	
}
