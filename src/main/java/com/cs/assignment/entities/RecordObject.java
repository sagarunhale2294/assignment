package com.cs.assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("records")
@Data
@Builder
@AllArgsConstructor
public class RecordObject {
	private String id;
	private Long duration;
	private String type;
	private String host;
	private boolean alert;
}
