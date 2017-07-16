package xivvic.console.interact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
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

	@Test(expected = NullPointerException.class)
	public void onGSFLWD_withNullChoices_thenThrowException()
	{
		subject.getStringFromListWithDefault(null, "Hello", 0);
	}

	@Test
	public void onGSFLWD_withEmptyChoices_thenReturnNull()
	{
		List<String> empty = Arrays.asList();
		assertNull(subject.getStringFromListWithDefault(empty, "Please choose", 0));
	}

	@Test
	public void onGSFLWD_withNegativeDefault_thenReturnNull()
	{
		List<String> choices = Arrays.asList("a");
		assertNull(subject.getStringFromListWithDefault(choices, "Please choose", -1));
	}

	@Test
	public void onGSFLWD_withDefaultOutOfRange_thenReturnNull()
	{
		List<String> choices = Arrays.asList("a");
		assertNull(subject.getStringFromListWithDefault(choices, "Please choose", 3));
	}

	@Test
	public void onGSFLWD_withEmptyResponse_thenChooseDefault()
	{
		// Arrange
		//
		int dv = 1;
		List<String> choices = Arrays.asList("a", "b", "c");
		InputStream is = inputStreamForString("\n");
		subject = new Stdin(is, printstream);

		// Act
		//
		String choice = subject.getStringFromListWithDefault(choices, "Please choose", dv);

		// Assert
		//
		assertEquals(choices.get(dv), choice);
	}

	@Test
	public void onGSFLWD_withValidStringResponse_thenReturnChoice()
	{
		// Arrange
		//
		int dv = 1;
		String target = "ape";
		List<String> choices = Arrays.asList("apple", "app", target, "ant");
		InputStream is = inputStreamForString("ape\n");
		subject = new Stdin(is, printstream);

		// Act
		//
		String choice = subject.getStringFromListWithDefault(choices, "Please choose", dv);

		// Assert
		//
		assertEquals(target, choice);
	}

	@Test
	public void onGSFLWD_withEventualValidStringResponse_thenReturnChoice()
	{
		// Arrange
		//
		int dv = 1;
		String target = "ape";
		List<String> choices = Arrays.asList("apple", "app", target, "ant");
		InputStream is = inputStreamForString("blast\nape\n");
		subject = new Stdin(is, printstream);

		// Act
		//
		String choice = subject.getStringFromListWithDefault(choices, "Please choose", dv);

		// Assert
		//
		assertEquals(target, choice);
	}


	@Test
	public void onGSFLWD_withEventualValidIndexResponse_thenReturnChoice()
	{
		// Arrange
		//
		int dv = 1;
		String target = "ape";
		List<String> choices = Arrays.asList("apple", "app", target, "ant");
		InputStream is = inputStreamForString("blast\n2\n");
		subject = new Stdin(is, printstream);

		// Act
		//
		String choice = subject.getStringFromListWithDefault(choices, "Please choose", dv);

		// Assert
		//
		assertEquals(target, choice);
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
