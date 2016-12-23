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
			trace = new Trace(id, Main.requests.get(id));
			Main.output.put(id, trace.toJson());
		} catch (JsonProcessingException e) {
			System.err.println(String.format("Fail to convert to json trace %s", trace.getId()));
		} finally {
			Main.requests.remove(id);
		}
	}
}
