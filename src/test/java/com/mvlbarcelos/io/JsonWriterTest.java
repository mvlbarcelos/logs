package com.mvlbarcelos.io;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.mvlbarcelos.Main;

public class JsonWriterTest {

	private static final String OUTPUT_ONE = "Output one";
	private static final String OUTPUT_TWO = "Output two";

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private JsonWriter jsonWriter;

	@Before
	public void setUp() {
		jsonWriter = new JsonWriter();
		Main.output.put("1", OUTPUT_ONE);
		Main.output.put("2", OUTPUT_TWO);
		System.setOut(new PrintStream(outContent));
	}

	@Test
	public void test() {
		jsonWriter.print();

		assertThat(Main.output.size(), is(0));
		assertTrue(outContent.toString().contains(OUTPUT_ONE));
		assertTrue(outContent.toString().contains(OUTPUT_TWO));
	}

}
