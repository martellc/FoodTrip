package com.foodtrip.ftmodelws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TripView  implements Serializable {

	private ProductWS product;
	
	private CompanyWS producer;
	
	private FoodStepWS foodGraph;

	
	private List<FoodStepWS> flatFoodGraph = new ArrayList<FoodStepWS>();
	
	private String[] paths;
	
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

	public FoodStepWS getFoodGraph() {
		return foodGraph;
	}


	public void setFoodGraph(FoodStepWS foodGraph) {
		this.foodGraph = foodGraph;
	}


	public List<FoodStepWS> getFlatFoodGraph() {
		return flatFoodGraph;
	}


	public void setFlatFoodGraph(List<FoodStepWS> flatGraph) {
		this.flatFoodGraph = flatGraph;
	}


	public String[] getPaths() {
		return paths;
	}


	public void setPaths(String[] paths) {
		this.paths = paths;
	}
}
