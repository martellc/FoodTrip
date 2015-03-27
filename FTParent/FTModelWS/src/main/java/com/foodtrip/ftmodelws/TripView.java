package com.foodtrip.ftmodelws;

import java.io.Serializable;
import java.util.Collection;

public class TripView  implements Serializable {

	private ProductWS product;
	
	private CompanyWS producer;
	
	private FoodStepWS foodGraph;

	private String path;
	
	public ProductWS getProduct() {
		return product;
	}

	
	public void setProduct(ProductWS product) {
		this.product = product;
	}

	public CompanyWS getProducer() {
		return producer;
	}
	
	public void setProducer(CompanyWS producer) {
		this.producer = producer;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public FoodStepWS getFoodGraph() {
		return foodGraph;
	}


	public void setFoodGraph(FoodStepWS foodGraph) {
		this.foodGraph = foodGraph;
	}
}
