package com.mvlbarcelos.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mvlbarcelos.Main;
import com.mvlbarcelos.trace.Request;

public class TraceUtilsTest {

	private Request request;
	
	@Before
	public void setUp(){
		request = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 22buxmqp->bm6il56t");
	}

	@Test
	public void shouldCreateItemInMapWhenKeyDoesNotExist() {
		TraceUtils.putRequestInMap(request);
		List<Request> requests = Main.requests.get(request.getId());
		assertThat(requests.size(), is(1));
	}
	
	@Test
	public void shouldPutItemInMapWhenKeyExists() throws Exception {
		Request request2 = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 null->bm6il56t");
		TraceUtils.putRequestInMap(request);
		TraceUtils.putRequestInMap(request2);
		List<Request> requests = Main.requests.get(request2.getId());
		assertThat(requests.size(), is(2));
	}
	
	
	@After
	public void after(){
		Main.requests.remove(request.getId());
	}
}
