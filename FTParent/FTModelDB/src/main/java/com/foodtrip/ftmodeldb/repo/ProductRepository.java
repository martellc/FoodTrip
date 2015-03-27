package com.foodtrip.ftmodeldb.repo;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.foodtrip.ftmodeldb.model.Product;

public interface ProductRepository extends GraphRepository<Product> {
	

	@Query("match (n:Product)-[:PRODUCED_BY]-(b:Company) where id(b)={0} return n")
	public Iterable<Product> getProducts(Long farmerID);

	
	@Query("match (n:Order)-[:REFER_TO]-(b:Product) where id(b)={0} return n")
	public Iterable<Product> getOrders(Long farmerID);
}

