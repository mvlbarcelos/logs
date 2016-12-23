package com.mvlbarcelos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	
	public static Map<String, List<Request>> requests = new HashMap<>();

	public static void main(String[] args) throws IOException, InterruptedException {
		 new LogReader(args[0]);
	}
}
