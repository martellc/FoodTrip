package com.foodtrip.ftmodeldb.repo;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.foodtrip.ftmodeldb.model.Company;

public interface NotificationRepository extends GraphRepository<Company> {
		
	@Query("match (n:Notification)-[:NOTIFY]->(c:Company) where id(c)={0} return n")
    public List<Company> getNotifications(Long companyID);

}