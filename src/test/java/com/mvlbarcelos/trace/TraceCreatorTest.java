package com.mvlbarcelos.trace;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.mvlbarcelos.Main;
import com.mvlbarcelos.util.TraceUtils;

public class TraceCreatorTest {
	
	private Request request2;
	private Request request;

	@Before
	public void setUp(){
		request2 = new Request("2013-10-23T10:12:35.318Z 2013-10-23T10:12:35.370Z eckakaau service3 bm6il56t->22buxmqp");
		request = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 null->bm6il56t");
		TraceUtils.putRequestInMap(request);
		TraceUtils.putRequestInMap(request2);
		
	}
	
	@Test
	public void shouldCreateJsonToWriteAndRemoveFromRequests() {
		TraceCreator traceCreator = new TraceCreator(request.getId());
		traceCreator.run();
		assertThat(Main.output.size(), is(1));
		assertThat(Main.requests.size(), is(0));
	}
}
