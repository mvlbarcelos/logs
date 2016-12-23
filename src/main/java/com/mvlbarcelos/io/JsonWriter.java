package com.mvlbarcelos.io;

import com.mvlbarcelos.Main;

public class JsonWriter implements Runnable {

	private static final long ONE_SECOND = 1000;

	@Override
	public void run() {
		while (true) {
			if (!Main.output.isEmpty()) {

				for (String key : Main.output.keySet()) {
					System.out.println(Main.output.get(key));
					Main.output.remove(key);
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
	}
}
