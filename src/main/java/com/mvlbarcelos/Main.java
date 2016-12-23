package com.mvlbarcelos;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mvlbarcelos.trace.Request;

public class Main {
	
	public static Map<String, List<Request>> requests = new ConcurrentHashMap<>();
	public static Map<String, String> output = new ConcurrentHashMap<>();
	public static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	public static void main(String[] args) throws IOException, InterruptedException {
		 new LogReader(args[0]);
	}
}
