package com.foodtrip.ftmodeldb.repo;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.foodtrip.ftmodeldb.model.Farm;

public interface FarmRepository extends GraphRepository<Farm> {

	}

