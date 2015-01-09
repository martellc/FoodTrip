package com.foodtrip.ftmodeldb.repo;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.foodtrip.ftmodeldb.model.Company;

public interface CompanyRepository extends GraphRepository<Company> {

	@Query("start n=node({0}) match (n)<-[* {originalOrderID:{1}}]-(Company) return Company")
    public Iterable<Company> getCompaniesPath(Long endCompany, Long orderID);
	
	@Query("MATCH p =(a:Company)-[* { originalOrderID:{0}} ]-(b:Company) RETURN p")
    public List<Company> getCompaniesPath(Long orderID);

}

