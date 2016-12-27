package com.mvlbarcelos.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mvlbarcelos.Main;
import com.mvlbarcelos.trace.Request;
import com.mvlbarcelos.trace.TraceInfo;

public class TraceUtilsTest {

	private Request request;

	@Before
	public void setUp() {
		request = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 22buxmqp->bm6il56t");
		Main.latestTimeRead = null;
	}

	@Test
	public void shouldSetLatestTimeReadWhenLatestTimeReadIsNull() throws Exception {
		TraceUtils.putRequestInMap(request);
		assertThat(Main.latestTimeRead, is(request.getStart()));
	}

	@Test
	public void shouldSetLatestTimeReadWhenLatestTimeReadIsBeforeRequestStart() throws Exception {
		Main.latestTimeRead = request.getStart().minusSeconds(1);
		TraceUtils.putRequestInMap(request);
		assertThat(Main.latestTimeRead, is(request.getStart()));
	}

	@Test
	public void shouldNotSetLatestTimeReadWhenLatestTimeReadIsAfterRequestStart() throws Exception {
		Main.latestTimeRead = request.getStart().plusSeconds(1);
		TraceUtils.putRequestInMap(request);
		assertThat(Main.latestTimeRead, is(request.getStart().plusSeconds(1)));
	}

	@Test
	public void shouldCreateItemInMapWhenKeyDoesNotExist() {
		TraceUtils.putRequestInMap(request);
		TraceInfo traceInfo = Main.requests.get(request.getId());
		assertThat(traceInfo.getRequests().size(), is(1));
	}

	@Test
	public void shouldPutItemInMapWhenKeyExists() throws Exception {
		Request request2 = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 null->bm6il56t");
		TraceUtils.putRequestInMap(request);
		TraceUtils.putRequestInMap(request2);
		TraceInfo traceInfo = Main.requests.get(request2.getId());
		assertThat(traceInfo.getRequests().size(), is(2));
	}

	@After
	public void after() {
		Main.requests.remove(request.getId());
		Main.latestTimeRead = null;
	}
}
