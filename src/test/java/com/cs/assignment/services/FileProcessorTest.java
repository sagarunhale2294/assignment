package com.cs.assignment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.cs.assignment.entities.Record;
import com.cs.assignment.entities.RecordObject;

@Slf4j
public class FileProcessorTest {

	//TODO: specify absolute file path here
	// Can add here classpath: location as well but the requirement is for url string only.
	public static final String url = "/Users/sagarkumar.unhale/Downloads/assignment/src/main/resources/logfile.txt";
	public static FileProcessor fileProcessor;

	@BeforeAll
	public static void setup() {
		fileProcessor = new FileProcessor();
	}

	@Test
	public void testParse() {
		log.debug("Running test case for FileProcessor::parse method.");
		List<Record> recordList = fileProcessor.parse(url);
		assertNotNull(recordList, "Record List is not null");
		assertEquals(recordList.size(), 5);
	}

	@Test
	public void testParseException() {
		log.debug("Running test case for FileProcessor::parse method.");
		try {
			List<Record> recordList = fileProcessor.parse("/logfile.txt");
		} catch (Exception exception) {
			assertTrue(exception instanceof NoSuchFileException);
		}
	}

	@Test
	public void calculateTimeStamp() {
		log.debug("Running test case for FileProcessor::calculateTimeSpan method.");
		List<Record> records = Arrays.asList(
			new Record("scsmbstga", "STARTED", "APPLICATION_LOG", "12345", 1491377495212L, false),
			new Record("scsmbssdj", "STARTED", null, null, 1491377495212L, false),
			new Record("scsmbstga", "FINISHED", "APPLICATION_LOG", "12345", 1491377495214L, false)
		);

		Map<Record, Long> recordMap = fileProcessor.calculateTimeSpan(records);
		assertNotNull(recordMap);
		assertEquals(recordMap.size(), 2);
	}

	@Test
	public void setFlagAndGenerateRecordObjectsTest() {
		log.debug("Running test case for FileProcessor::setFlagAndGenerateRecordObjects method.");
		List<Record> records = Arrays.asList(
			new Record("scsmbstga", "STARTED", "APPLICATION_LOG", "12345", 1491377495212L, false),
			new Record("scsmbstga", "FINISHED", "APPLICATION_LOG", "12345", 1491377495214L, false)
		);

		Map<Record, Long> recordMap = fileProcessor.calculateTimeSpan(records);
		List<RecordObject> recordObjects = fileProcessor.setFlagAndGenerateRecordObjects(recordMap);
		assertNotNull(recordObjects);
		assertEquals(recordObjects.size(), 1);
	}
}
