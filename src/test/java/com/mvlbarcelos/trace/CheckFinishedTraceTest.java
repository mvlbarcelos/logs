package com.mvlbarcelos.trace;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mvlbarcelos.Main;
import com.mvlbarcelos.util.TraceUtils;

@RunWith(MockitoJUnitRunner.class)
public class CheckFinishedTraceTest {
	
	@InjectMocks
	private CheckFinishedTrace checkFinishedTrace = new CheckFinishedTrace();
	
	@Mock
	private ThreadPoolExecutor executor;
	
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	private Request rootRequest;
	
	@Before
	public void setUp(){
		System.setErr(new PrintStream(errContent));
		Request request = new Request("2013-10-23T10:12:35.371Z 2013-10-23T10:12:35.471Z eckakaau2 service6 22buxmqp->bm6il56t");
		rootRequest = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau2 service6 null->bm6il56t");
		TraceUtils.putRequestInMap(request);
	}

	@Test
	public void shouldSubmitTaskOnlyWhenTraceIsFinished() {
		TraceUtils.putRequestInMap(rootRequest);
		Main.latestTimeRead = rootRequest.getStart().plusSeconds(31);
		checkFinishedTrace.check();
		verify(executor).submit(any(TraceCreator.class));
	}
	
	@Test
	public void shouldNotSubmitTaskWhenTraceIsNotFinished() throws Exception {
		TraceUtils.putRequestInMap(rootRequest);
		Main.latestTimeRead = rootRequest.getStart().plusSeconds(28);
		checkFinishedTrace.check();
		verifyZeroInteractions(executor);
	}
	
	
	@Test
	public void shouldNotSubmitTaskWhenTraceHaveNoRoot() throws Exception {
		Main.latestTimeRead = rootRequest.getStart().plusSeconds(31);
		checkFinishedTrace.check();
		verifyZeroInteractions(executor);
		assertTrue(errContent.toString().contains("2013-10-23T10:12:35.371Z 2013-10-23T10:12:35.471Z eckakaau2 service6 22buxmqp->bm6il56t"));
		assertThat(Main.requests.size(), is(0));
	}
	
	@After
	public void after(){
		Main.requests.clear();
	}
}
