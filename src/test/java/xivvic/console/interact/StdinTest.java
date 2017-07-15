package xivvic.console.interact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// import static org.junit.Assert.*;
// import static org.mockito.Mockito.*;

public class StdinTest
{
	private static final double EPSILON = 0.0001;

	private ByteArrayOutputStream out_bytes;
	private PrintStream  printstream;
	private InputStream yes_input;
	private InputStream  no_input;

	private Stdin subject;

	@Before
	public void before()
	{
		yes_input = inputStreamForString("yes\n");
		no_input  = inputStreamForString("no\n");

		out_bytes = new ByteArrayOutputStream();
		printstream = new PrintStream(out_bytes);

		subject = new Stdin(yes_input, printstream);
	}

	@After
	public void after()
	{
		yes_input = null;
		no_input = null;
		out_bytes = null;
		printstream = null;
	}

	@Test
	public void onConfirm_withNoPrompt_thenPromptIsAreYouSure() throws Exception
	{
		// Arrange
		//
		String prompt = null;

		// Act
		//
		subject.confirm(prompt, true);

		// Assert
		//
		assertEquals(Stdin.DEFAULT_CONFIRM_PROMPT, out_bytes.toString());
	}

	@Test
	public void onConfirm_withNoAnswer_thenReturnFalse() throws Exception
	{
		// Arrange
		//
		String prompt = "Steady on Jack. You're sure? ";
		subject = new Stdin(no_input, printstream);


		// Act
		//
		boolean b = subject.confirm(prompt, true);

		// Assert
		//
		assertFalse(b);
	}


	@Test
	public void onConfirm_withYAnswer_thenReturnTrue() throws Exception
	{
		// Arrange
		//
		String prompt = "Steady on Jack. You're sure? ";
		yes_input = inputStreamForString("y\n");

		subject = new Stdin(yes_input, printstream);


		// Act
		//
		boolean b = subject.confirm(prompt, true);

		// Assert
		//
		assertTrue(b);
	}

	@Test
	public void onGetString_withNewline_thenReturnEmptyString() throws Exception
	{
		// Arrange
		//
		InputStream is = inputStreamForString("\n");
		subject = new Stdin(is, printstream);

		// Act
		//
		String s = subject.getString();

		// Assert
		//
		assertNotNull(s);
		assertEquals("", s);
	}

	@Test
	public void onGetString_withRegularString_thenReturnThatString() throws Exception
	{
		// Arrange
		//
		String s = "This is a regular string.";;
		InputStream is = inputStreamForString(s + "\n");
		subject = new Stdin(is, printstream);

		// Act
		//
		String result = subject.getString();

		// Assert
		//
		assertNotNull(result);
		assertEquals(s, result);
	}

	@Test
	public void onGetInteger_withIntegerString_thenReturnThatInteger() throws Exception
	{
		// Arrange
		//
		String s = "-10";
		InputStream is = inputStreamForString(s + "\n");
		subject = new Stdin(is, printstream);

		// Act
		//
		int result = subject.getInt();

		// Assert
		//
		assertEquals(-10, result);
	}

	@Test
	public void onGetDouble_withDoubleString_thenReturnThatInteger() throws Exception
	{
		// Arrange
		//
		String s = "1.654\n";
		InputStream is = inputStreamForString(s);
		subject = new Stdin(is, printstream);

		// Act
		//
		double result = subject.getDouble();

		// Assert
		//
		assertEquals(1.654, result, EPSILON);
	}


	///////////////////////////////
	// Helpers                   //
	///////////////////////////////

	private InputStream inputStreamForString(String s)
	{
		Objects.requireNonNull(s);

		byte[]   bytes = s.getBytes(StandardCharsets.UTF_8);
		InputStream rv = new ByteArrayInputStream(bytes);

		return rv;
	}
}
