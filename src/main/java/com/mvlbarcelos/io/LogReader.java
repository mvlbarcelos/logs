
package com.mvlbarcelos.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mvlbarcelos.trace.Request;
import com.mvlbarcelos.trace.TraceCreator;
import com.mvlbarcelos.util.TraceUtils;

public class LogReader implements Runnable{

	private static final long ONE_SECOND = 1000;
	private int totalCores;
	private ThreadPoolExecutor executor;

	public LogReader() {
		totalCores = Runtime.getRuntime().availableProcessors();
		executor = new ThreadPoolExecutor(totalCores,
										  totalCores,
										  1000,
										  TimeUnit.MILLISECONDS,
										  new LinkedBlockingQueue<Runnable>());
	}

	@Override
	public void run() {
		String filePath = System.getProperty("inputFile");
		try {
			BufferedReader input = new BufferedReader(new FileReader(filePath));
			String currentLine = null;
			while (true) {
				if ((currentLine = input.readLine()) != null) {
					parseLineToRequest(currentLine);
					continue;
				}
				try {
					Thread.sleep(ONE_SECOND);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
			input.close();

		} catch (IOException e) {
			System.err.println(String.format("Error to read the file %s", filePath));
			System.exit(0);
		}
	}

	void parseLineToRequest(String currentLine) {
		try {
			Request request = new Request(currentLine);

			TraceUtils.putRequestInMap(request);
			if (request.isRoot()) {
				executor.submit(new TraceCreator(request.getId()));
			}

		} catch (Exception e) {
			System.err.println(currentLine);
		}
	}
}
