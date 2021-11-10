package com.cs.assignment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sun.istack.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Record {

	@Id
	@NotNull
	@EqualsAndHashCode.Include
	private String id;
	private String state;
	private String type;
	private String host;
	private long timestamp;
	private boolean flag;
}
