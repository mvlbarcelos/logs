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
		try {
			Trace trace = new Trace(id, Main.requests.get(id));
			Main.output.put(id, trace.writeJson());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} finally {
			Main.requests.remove(id);
		}
	}
}
