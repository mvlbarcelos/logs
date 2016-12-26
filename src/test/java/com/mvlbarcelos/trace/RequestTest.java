package com.mvlbarcelos.trace;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mvlbarcelos.exception.InvalidLineException;
import com.mvlbarcelos.trace.Request;

public class RequestTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	private Request request;

	@Test
	public void shouldValidateLineInformation() throws Exception {
		expectedEx.expect(InvalidLineException.class);
		expectedEx.expectMessage("The line should have 5 elements, but have 3.");
		request = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau");
	}

	@Test
	public void shouldBeRootWhenCallerSpanIsNull() {
		request = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 null->bm6il56t");
		assertThat(request.isRoot(), is(true));
	}

	@Test
	public void shouldBeNotRootWhenCallerSpanIsNotNull() throws Exception {
		request = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 22buxmqp->bm6il56t");
		assertThat(request.isRoot(), is(false));
	}

	@Test
	public void shouldParseLineToRequest() throws Exception {
		request = new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 22buxmqp->bm6il56t");
		assertThat(request.getId(), is("eckakaau"));
		assertThat(request.getService(), is("service6"));
		assertThat(request.getCallerSpan(), is("22buxmqp"));
		assertThat(request.getSpan(), is("bm6il56t"));
		assertThat(request.getStart().toString(), is("2013-10-23T10:12:35.271"));
		assertThat(request.getEnd().toString(), is("2013-10-23T10:12:35.471"));
	}

}
