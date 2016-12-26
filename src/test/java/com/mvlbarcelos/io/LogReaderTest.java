package com.mvlbarcelos.io;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class LogReaderTest {

	private LogReader logReader;

	

	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUp() {
		logReader = new LogReader();
		System.setErr(new PrintStream(errContent));
	}

	@Test
	public void shouldNotSubmitWhenInvalidLine() {
		String currentLine = "2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Zeckakaau2 service6 22buxmqp->bm6il56t";
		logReader.parseLineToRequest(currentLine);

		assertTrue(errContent.toString().contains(currentLine));
	}

}
