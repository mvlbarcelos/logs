package com.mvlbarcelos.trace;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mvlbarcelos.trace.Request;
import com.mvlbarcelos.trace.Trace;

public class TraceTest {

	private static final String ID = "eckakaau";
	private Trace trace;
	private String json = "{\"id\":\"eckakaau\",\"root\":{\"start\":\"2013-10-23T10:12:35.271\",\"end\":\"2013-10-23T10:12:35.471\",\"service\":\"service6\",\"span\":\"bm6il56t\",\"calls\":[{\"start\":\"2013-10-23T10:12:35.286\",\"end\":\"2013-10-23T10:12:35.302\",\"service\":\"service9\",\"span\":\"zfjlsiev\",\"calls\":[{\"start\":\"2013-10-23T10:12:35.293\",\"end\":\"2013-10-23T10:12:35.302\",\"service\":\"service7\",\"span\":\"d6m3shqy\",\"calls\":[{\"start\":\"2013-10-23T10:12:35.298\",\"end\":\"2013-10-23T10:12:35.3\",\"service\":\"service3\",\"span\":\"62d45qeh\",\"calls\":[]}]}]},{\"start\":\"2013-10-23T10:12:35.318\",\"end\":\"2013-10-23T10:12:35.37\",\"service\":\"service3\",\"span\":\"22buxmqp\",\"calls\":[{\"start\":\"2013-10-23T10:12:35.339\",\"end\":\"2013-10-23T10:12:35.342\",\"service\":\"service1\",\"span\":\"nhxtegwv\",\"calls\":[{\"start\":\"2013-10-23T10:12:35.339\",\"end\":\"2013-10-23T10:12:35.339\",\"service\":\"service1\",\"span\":\"4zhimp35\",\"calls\":[]}]},{\"start\":\"2013-10-23T10:12:35.345\",\"end\":\"2013-10-23T10:12:35.361\",\"service\":\"service5\",\"span\":\"3wos67cv\",\"calls\":[]}]}]}}";
	
	@Test
	public void shouldCreateTraceFromRequests() throws JsonProcessingException {
		trace = new Trace(ID, createRequests());

		assertThat(trace.getId(), is(ID));
		Assert.assertNotNull(trace.getRoot());
		assertThat(trace.getRoot().isRoot(), is(true));
		assertThat(trace.toJson(), is(json));
	}

	private List<Request> createRequests() {
		return Arrays.asList(new Request[] {
				new Request("2013-10-23T10:12:35.298Z 2013-10-23T10:12:35.300Z eckakaau service3 d6m3shqy->62d45qeh"),
				new Request("2013-10-23T10:12:35.293Z 2013-10-23T10:12:35.302Z eckakaau service7 zfjlsiev->d6m3shqy"),
				new Request("2013-10-23T10:12:35.286Z 2013-10-23T10:12:35.302Z eckakaau service9 bm6il56t->zfjlsiev"),
				new Request("2013-10-23T10:12:35.339Z 2013-10-23T10:12:35.339Z eckakaau service1 nhxtegwv->4zhimp35"),
				new Request("2013-10-23T10:12:35.339Z 2013-10-23T10:12:35.342Z eckakaau service1 22buxmqp->nhxtegwv"),
				new Request("2013-10-23T10:12:35.345Z 2013-10-23T10:12:35.361Z eckakaau service5 22buxmqp->3wos67cv"),
				new Request("2013-10-23T10:12:35.318Z 2013-10-23T10:12:35.370Z eckakaau service3 bm6il56t->22buxmqp"),
				new Request("2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau service6 null->bm6il56t") });
	}
	

}
