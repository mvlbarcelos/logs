package com.mvlbarcelos.util;

import com.mvlbarcelos.Main;
import com.mvlbarcelos.trace.Request;
import com.mvlbarcelos.trace.TraceInfo;

public class TraceUtils {

	public static void putRequestInMap(Request request) {
		setLatestTimeRead(request);
		TraceInfo traceInfo = Main.requests.get(request.getId());
		if (traceInfo == null) {
			traceInfo = new TraceInfo(request);
			Main.requests.put(request.getId(), traceInfo);
		} else {
			traceInfo.addRequest(request);
		}
	}

	private static void setLatestTimeRead(Request request) {
		if (Main.latestTimeRead == null || Main.latestTimeRead.isAfter(request.getStart())) {
			Main.latestTimeRead = request.getStart();
		}
	}
}
