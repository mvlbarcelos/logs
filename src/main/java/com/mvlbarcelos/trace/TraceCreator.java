package com.mvlbarcelos.trace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mvlbarcelos.Main;

public class TraceCreator implements Runnable {

	private String id;

	public TraceCreator(String id) {
		this.id = id;
	}

	@Override
	public void run() {
		Trace trace = null;
		try {
			TraceInfo traceInfo = Main.requests.get(id);
			trace = new Trace(id, traceInfo.getRequests());
			Main.output.put(id, trace.toJson());
		} catch (JsonProcessingException e) {
			System.err.println(String.format("Fail to convert to json trace %s", trace.getId()));
		} finally {
			Main.requests.remove(id);
		}
	}
}
