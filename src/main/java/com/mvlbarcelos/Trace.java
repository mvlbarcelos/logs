package com.mvlbarcelos;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Trace {

	private String id;
	private Request root;
	
	private ObjectMapper mapper = new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	public Trace(String id, List<Request> request) {
		this.id = id;
		createCallsTree(request);
	}

	private void createCallsTree(List<Request> requests) {
		for (Request request : requests) {
			if (request.getCallerSpan() == null) {
				root = request;
			}
			request.setCalls(filterRequestBySpan(request.getSpan(), requests));
		}
	}

	private List<Request> filterRequestBySpan(String span, List<Request> requests) {
		return requests
				.stream()
				.filter(r -> span.equals(r.getCallerSpan()))
				.collect(Collectors.toList());
	}

	public String getId() {
		return id;
	}

	public Request getRoot() {
		return root;
	}
	
	public String writeJson() throws JsonProcessingException{
		return mapper.writeValueAsString(this);
	}
}