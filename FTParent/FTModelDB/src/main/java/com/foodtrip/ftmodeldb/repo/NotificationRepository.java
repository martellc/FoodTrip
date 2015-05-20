package com.foodtrip.ftmodeldb.repo;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.foodtrip.ftmodeldb.model.Notification;

public interface NotificationRepository extends GraphRepository<Notification> {
		
	@Query("match (n:Notification)-[:NOTIFY]->(c:Company) where id(c)={0} return n ORDER BY n.date DESC")
    public List<Notification> getNotifications(Long companyID);

}