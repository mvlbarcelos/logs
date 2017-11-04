package com.mvlbarcelos.trace;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mvlbarcelos.Main;

public class TraceInfoTest {
	
	private TraceInfo traceInfo;
	private Request rootRequest;
	private Request request;
	
	@Before
	public void setUp(){
		request = new Request("2013-10-23T10:12:35.471Z 2013-10-23T10:12:35.771Z eckakaau service6 bm6il56t->22buxmqp");
		rootRequest = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 null->bm6il56t");
		traceInfo = new TraceInfo(request);
		Main.latestTimeRead = rootRequest.getStart().plusSeconds(29);
	}
	
	@Test
	public void shouldNotChangeStartWhenSetDateAfterThanStart() {
		Request request2 = new Request("2013-10-23T10:12:36.318Z 2013-10-23T10:12:35.370Z eckakaau service3 bm6il56t->22buxmqp");
		traceInfo.addRequest(request2);
		assertThat(traceInfo.getStart(), is(request.getStart()));
	}

	@Test
	public void shouldChangeStartWhenSetDateBeforeThanStart() {
		Request request2 = new Request("2013-10-23T10:12:35.318Z 2013-10-23T10:12:35.370Z eckakaau service3 bm6il56t->22buxmqp");
		traceInfo.addRequest(request2);
		assertThat(traceInfo.getStart(), is(request2.getStart()));
	}
	
	@Test
	public void shouldHasRootIsFalseWhenNotHaveRootRequest() throws Exception {
		assertThat(traceInfo.haveRoot(), is(false));
	}
	
	@Test
	public void shouldHasRootWhenHaveRootRequest() throws Exception {
		traceInfo.addRequest(rootRequest);
		assertThat(traceInfo.haveRoot(), is(true));
	}
	
	
	@Test
	public void shouldBeNotFinishedWhenHStartTimeIsBeforeTimeOutTime() throws Exception {
		Main.latestTimeRead = rootRequest.getStart().plusSeconds(29);
		assertThat(traceInfo.isFinished(), is(false));
	}
	
	@Test
	public void shouldBeFinishedWhenHStartTimeIsAfterTimeOutTime() throws Exception {
		Main.latestTimeRead = rootRequest.getStart().plusSeconds(31);
		assertThat(traceInfo.isFinished(), is(true));
	}
	
}
