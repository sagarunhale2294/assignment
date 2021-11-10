package com.cs.assignment.services;

import static com.cs.assignment.constants.UtilityConstants.TIME_SPAN;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.cs.assignment.entities.Record;
import com.cs.assignment.entities.RecordObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class FileProcessor {

	public List<RecordObject> processFile(String fileUrl) {
		log.info("Processing file: {}", fileUrl);
		Map<Record, Long> recordTimeSpanMap = calculateTimeSpan(parse(fileUrl));
		return setFlagAndGenerateRecordObjects(recordTimeSpanMap);
	}

	/**
	 * Parsing file data to List<Record>
	 *
	 * @param uri
	 *
	 * @return List<Record>
	 */
	public List<Record> parse(String uri) {
		log.info("Parsing file for uri: {}", uri);
		List<Record> records = new ArrayList<>();
		try {
			List<String> fileContent = Files.lines(Paths.get(uri))
				.collect(Collectors.toList());
			records = new ObjectMapper().readValue(fileContent.toString(), new TypeReference<List<Record>>() {
			});
		} catch (IOException ioException) {
			log.error("Error while parsing file. Exception:{}", ioException);
		}
		return records;
	}

	/**
	 * Calculating timespan and returning MAP of activity and their timespan
	 *
	 * @param records
	 *
	 * @return Map<Record, Long>
	 */
	public Map<Record, Long> calculateTimeSpan(List<Record> records) {
		log.info("Calculating time span for activities...");
		Map<Record, Long> processedRecordMap = new LinkedHashMap<>();
		records.stream().forEach(record -> {
			Long timeStamp = (processedRecordMap.containsKey(record)) ? Math.abs(processedRecordMap.get(record) - record.getTimestamp()) : record.getTimestamp();
			processedRecordMap.put(record, timeStamp);
		});

		// filtering out the record () -> timespan < 4
		Predicate<Long> predicate = (value) -> value < TIME_SPAN;
		log.info("Filtering records list having timespan < {}", TIME_SPAN);
		return processedRecordMap;
	}

	/**
	 * Set flag and map Record to RecordObject
	 *
	 * @param processedRecords
	 *
	 * @return List<RecordObject>
	 */
	public List<RecordObject> setFlagAndGenerateRecordObjects(Map<Record, Long> processedRecords) {
		log.info("Mapping record data to recordObject...");
		List<RecordObject> recordList = new ArrayList<>();
		processedRecords.entrySet().stream().forEach(recordLongEntry -> {
			Record record = recordLongEntry.getKey();
			if (recordLongEntry.getValue() < TIME_SPAN) {
				record.setFlag(true);
			}
			recordList.add(RecordObject.builder()
				.id(record.getId())
				.duration(recordLongEntry.getValue())
				.host(record.getHost())
				.type(record.getType())
				.alert(record.isFlag())
				.build());
		});
		return recordList;
	}
}
