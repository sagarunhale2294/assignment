package com.cs.assignment.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs.assignment.entities.RecordObject;
import com.cs.assignment.repository.RecordsRepository;
import com.cs.assignment.services.FileProcessor;

@RestController
@RequestMapping(value = "/api/v1/cs/file", produces = "application/json", consumes = "application/json")
public class FileProcessingController {

	@Autowired
	FileProcessor fileProcessor;

	@Autowired
	RecordsRepository recordsRepository;

	@PostMapping(value = "/process")
	public ResponseEntity<List<RecordObject>> processFile(@RequestParam String url) {
		List<RecordObject> recordObjects = recordsRepository.saveAll(fileProcessor.processFile(url));
		return ResponseEntity.status(200).body(recordObjects);
	}

	@PostMapping(value = "/processWithFile")
	public ResponseEntity<Void> processFile(@RequestParam File file) {
		// Can get file in the request only instead of url
		// Process the file chunk by chunk by using multiple threads
		// declare the chunk size to 50.
		return null;
	}
}
