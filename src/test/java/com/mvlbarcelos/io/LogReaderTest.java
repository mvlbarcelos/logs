package com.mvlbarcelos.io;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.concurrent.ThreadPoolExecutor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mvlbarcelos.trace.TraceCreator;
@RunWith(MockitoJUnitRunner.class) 
public class LogReaderTest {
	
	@InjectMocks
	private LogReader logReader = new LogReader();
	
	@Mock
	private ThreadPoolExecutor executor;

	@Test
	public void shouldSubmitTaskOnlyWhenRequestIsRoot() {
		String currentLine = "2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau2 service6 null->bm6il56t";
		logReader.parseLineToRequest(currentLine);

		verify(executor).submit(any(TraceCreator.class));
	}
	
	@Test
	public void shouldNotSubmitTaskWhenRequestIsNotRoot() {
		String currentLine = "2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Z eckakaau2 service6 22buxmqp->bm6il56t";
		logReader.parseLineToRequest(currentLine);

		verifyZeroInteractions(executor);
	}
	
	@Test
	public void shouldNotSubmitWhenInvalidLine() {
		String currentLine = "2013-10-23T10:12:35.271Z 2013-10-23T10:12:35.471Zeckakaau2 service6 22buxmqp->bm6il56t";
		logReader.parseLineToRequest(currentLine);

		verifyZeroInteractions(executor);
	}

}
