package com.foodtrip.ftmodeldb.repo;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.foodtrip.ftmodeldb.model.Company;

public interface CompanyRepository extends GraphRepository<Company> {

	
	@Query("MATCH p =(a:Company)-[* { originalOrderID:{2}} ]-(b:Company) WHERE a.cID = {0} AND b.cID = {1} RETURN p")
    public List<Company> getCompaniesPath(Long startCompany,Long endCompany,Long orderID);
}

