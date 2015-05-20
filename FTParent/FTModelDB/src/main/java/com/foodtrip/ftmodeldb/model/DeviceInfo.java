package com.foodtrip.ftmodeldb.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class DeviceInfo {

	@GraphId 
	private Long id;
	
	@Indexed(indexName="androidUUID", indexType=IndexType.FULLTEXT)
	private Long androidUUID;
	
	private long date;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAndroidUUID() {
		return androidUUID;
	}
	public void setAndroidUUID(Long androidUUID) {
		this.androidUUID = androidUUID;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	
}
