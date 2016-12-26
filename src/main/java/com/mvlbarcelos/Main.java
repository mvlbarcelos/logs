
package com.mvlbarcelos;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mvlbarcelos.io.JsonWriter;
import com.mvlbarcelos.io.LogReader;
import com.mvlbarcelos.trace.TraceInfo;

public class Main {
	
	public static LocalDateTime latestTimeRead;
	public static Map<String, TraceInfo> requests = new ConcurrentHashMap<>();
	public static Map<String, String> output = new ConcurrentHashMap<>();
	public static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	public static void main(String[] args) throws IOException, InterruptedException {

		Runnable reader = new LogReader();
		Thread readerThread = new Thread(reader);
		readerThread.setName("reader");
		readerThread.start();

		Runnable write = new JsonWriter();
		Thread writerThread = new Thread(write);
		writerThread.setName("writer");
		writerThread.start();
	}
}
