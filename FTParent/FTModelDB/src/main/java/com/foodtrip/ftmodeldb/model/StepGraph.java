package com.foodtrip.ftmodeldb.model;

import java.util.HashMap;
import java.util.Map;

public class StepGraph {

	private Company node;
	private Map<Long,StepGraph> children =  new HashMap<Long,StepGraph>();

	public Map<Long, StepGraph> getChildren() {
		return children;
	}

	public void setChildren(Map<Long, StepGraph> children) {
		this.children = children;
	}

	public Company getNode() {
		return node;
	}

	public void setNode(Company node) {
		this.node = node;
	}

}
