package com.mvlbarcelos.trace;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mvlbarcelos.Main;

public class TraceInfo {

	private static final int TRIRTY_SECOND = 30;
	private String id;
	private LocalDateTime start;
	private boolean haveRoot;
	private List<Request> requests = new ArrayList<>();

	public TraceInfo(Request request) {
		this.id = request.getId();
		this.start = request.getStart();
		this.requests.add(request);
	}

	public String getId() {
		return id;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void addRequest(Request request) {
		if (this.start.isAfter(request.getStart())) {
			this.start = request.getStart();
		}
		if (request.isRoot()) {
			this.haveRoot = true;
		}
		this.requests.add(request);
	}

	public LocalDateTime getStart() {
		return start;
	}

	public boolean haveRoot() {
		return haveRoot;
	}

	public boolean isFinished() {
		return (start.isAfter(Main.latestTimeRead.plusSeconds(TRIRTY_SECOND)));
	}
}
