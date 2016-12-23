package com.mvlbarcelos.util;

import java.util.ArrayList;
import java.util.List;

import com.mvlbarcelos.Main;
import com.mvlbarcelos.Request;

public class TraceUtils {

	public static void putRequestInMap(Request request) {
		if (!Main.requests.containsKey(request.getId())) {
			List<Request> requests = new ArrayList<Request>();
			requests.add(request);
			Main.requests.put(request.getId(), requests);
		} else {
			Main.requests.get(request.getId()).add(request);
		}
	}
}
