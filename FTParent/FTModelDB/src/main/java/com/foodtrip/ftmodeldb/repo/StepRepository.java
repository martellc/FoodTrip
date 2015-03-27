package com.foodtrip.ftmodeldb.repo;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.foodtrip.ftmodeldb.model.Step;

public interface StepRepository extends GraphRepository<Step> {

//	@Query("start n=node({0}) match (n {orderID:{1}})-[STEP*]->(Step)  return n,Step")
//    public Iterable<Step> getSteps(Long start, Long orderID);

	@Query("start n=node({0}) match n-[r*]->m return distinct m")
    public Iterable<Step> getCurrentSteps(Long start);

}

