package com.mvlbarcelos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mvlbarcelos.exception.InvalidLineException;

public class Request {

	private static final String VALIDATION_MESSAGE_SIZE = "The line should have 5 elements, but have %s.";
	
	@JsonIgnore
	private String id;
	private String start;
	private String end;
	private String service;
	private String callerSpan;
	private String span;
	private List<Request> calls = new ArrayList<>();

	public Request(String line) {
		String[] informations = line.split(" ");
		validate(informations);

		this.start = informations[0];
		this.end = informations[1];
		this.id = informations[2];
		this.service = informations[3];
		String[] spans = informations[4].split("->");
		this.callerSpan = "null".equals(spans[0]) ? null : spans[0];
		this.span = spans[1];

	}

	private void validate(String[] informations) {
		if (informations.length != 5) {
			throw new InvalidLineException(String.format(VALIDATION_MESSAGE_SIZE, informations.length));
		}
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public String getService() {
		return service;
	}

	@JsonIgnore
	public String getCallerSpan() {
		return callerSpan;
	}

	public String getSpan() {
		return span;
	}

	public List<Request> getCalls() {
		return calls;
	}
	
	public String getId() {
		return id;
	}

	@JsonIgnore
	public boolean isRoot() {
		return this.getCallerSpan() == null;
	}

	public void setCalls(List<Request> calls) {
		this.calls = calls;
	}
}
