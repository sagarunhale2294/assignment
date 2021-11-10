package com.cs.assignment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cs.assignment.entities.RecordObject;


public interface RecordsRepository extends MongoRepository<RecordObject, String> {

	@Query("{id:'?0'}")
	RecordObject findRecordById(String id);

	@Query(value="{type:'?0'}", fields="{'type' : 1, 'host' : 1}")
	List<RecordObject> findAll(String type);

}