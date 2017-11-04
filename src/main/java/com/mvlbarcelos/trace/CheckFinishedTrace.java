package com.mvlbarcelos.trace;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mvlbarcelos.Main;

public class CheckFinishedTrace implements Runnable {

	private static final long ONE_SECOND = 1000;

	private int totalCores;
	private ThreadPoolExecutor executor;

	public CheckFinishedTrace() {
		totalCores = Runtime.getRuntime().availableProcessors();
		executor = new ThreadPoolExecutor(totalCores, totalCores, 1000, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	@Override
	public void run() {
		while (true) {
			if (!Main.requests.isEmpty()) {
				check();
				continue;
			}
			try {
				Thread.sleep(ONE_SECOND);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	void check() {

		Set<String> keys = Main.requests.keySet();
		for (String id : keys) {
			TraceInfo traceInfo = Main.requests.get(id);
			if (traceInfo != null) {
				if (traceInfo.isFinished()) {
					if (traceInfo.haveRoot()) {
						executor.submit(new TraceCreator(id));
					} else {
						traceInfo.getRequests().forEach(System.err::println);
						Main.requests.remove(traceInfo.getId());
					}
				}
			}
		}
	}

}
