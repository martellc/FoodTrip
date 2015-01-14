package com.foodtrip.ftmodelws;

import java.util.Collection;

public class TripView {

	private ProductWS product;
	
	private FarmWS producer;
	
	private Collection<FoodStepWS> steps;

	public ProductWS getProduct() {
		return product;
	}

	public void setProduct(ProductWS product) {
		this.product = product;
	}

	public FarmWS getProducer() {
		return producer;
	}

	public void setProducer(FarmWS producer) {
		this.producer = producer;
	}

	public Collection<FoodStepWS> getSteps() {
		return steps;
	}

	public void setSteps(Collection<FoodStepWS> steps) {
		this.steps = steps;
	}
}
