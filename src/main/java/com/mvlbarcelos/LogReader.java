package com.mvlbarcelos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.mvlbarcelos.util.TraceUtils;

public class LogReader {

	private static final long ONE_SECOND = 1000;
	private String filePath;

	public LogReader(String filePath) throws IOException {
		this.filePath = filePath;
		read();
	}

	private void read() throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(filePath));
		String currentLine = null;
		while (true) {

			if ((currentLine = input.readLine()) != null) {
				Request request = new Request(currentLine);

				TraceUtils.putRequestInMap(request);
				if (request.isRoot()) {
					new TraceWriter(request.getId());
				}
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
	}
}
