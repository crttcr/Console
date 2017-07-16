package xivvic.console.interact;

// Simple methods for reading from standard system input from the console.
// These methods will pause and wait for input until ENTER is pressed.
// The data is converted to the proper type and returned to the caller.
//
// A method is provided for each of the data types:
// int, float, double, char, Number and String
// The methods assume that each input value is terminated by a newline.
//
// If incorrect input is entered, a value of 0 or '\n' is returned, as applicable.
//
// Author history:
// 1998 Fall Charlie Clarke - original inception
// 1999 Spring Guy Lemieux - cleanup, restructured code, added a few methods
// 1999 Fall Anthony Cox - made Reader variables static to permit piping input
// Our latest email addresses or home pages are readily found using
// standard web search engines.
//
// Please feel free to use this code for your own Java programs,
// but we would appreciate it if you retain the credit to our names
// below. This way, you are acknowledging the true source of your
// code, and it will allow others to get the latest version from us.
//
// Thank you.
//
//
// CRT: Modified
//

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

public class Stdin
{
	public static String DEFAULT_CONFIRM_PROMPT = "Are you sure? ";

	private final BufferedReader in;
	private final PrintStream out;
	public Stdin(InputStream is, PrintStream out)
	{
		Objects.requireNonNull(is);
		this.out = Objects.requireNonNull(out);
		this.in  = new BufferedReader(new InputStreamReader(is));
	}

	public String getString()
	{
		try
		{
			return in.readLine();
		}
		catch (Exception e)
		{
			out.println("getString() exception, returning empty string");
			return "";
		}
	}

	public String getStringFromListWithDefault(List<String> choices, String prompt, int def)
	{
		Objects.requireNonNull(choices);

		if (def < 0 || def >= choices.size())
		{
			return null;
		}

		if (choices.size() == 0)
		{
			return null;
		}

		if (prompt == null || prompt.trim().length() == 0)
		{
			prompt = "Chose among the following items: ";
		}

		String dv = choices.get(def);
		String fmt = "%2d -- %s\n";

		while (true)
		{
			out.printf("%s [%s]", prompt, dv);
			for (int i = 0; i < choices.size(); i++)
			{
				out.printf(fmt, i, choices.get(i));
			}

			out.print("Selection --> ");

			String choice = getString();
			if (choice == null || choice.trim().length() == 0)
			{
				return dv;
			}

			Integer index = attemptToRecognizeUserInputAsChoice(choices, choice);
			if (index != null)
			{
				return choices.get(index.intValue());
			}

			out.printf("Response [%s] is not valid. Choose another item or its index", choice);
		}
	}



	/**
	 * Confirms a user action, returning true if the response is "y" or "yes"
	 *
	 */
	public boolean confirm(String prompt, boolean def)
	{
		if (prompt == null || prompt.length() == 0)
		{
			prompt = DEFAULT_CONFIRM_PROMPT;
		}

		try
		{
			out.print(prompt);
			String input = in.readLine();
			if (input == null || input.length() == 0)
			{
				return def;
			}

			String lower = input.trim().toLowerCase();

			if (lower.equals("y") || lower.equals("yes"))
			{
				return true;
			}

			return false;
		}
		catch (Exception e)
		{
			out.println("Exception confirming user action. Returning false.");
			return false;
		}
	}

	public String promptString(String prompt, String def)
	{
		if (prompt == null || prompt.length() == 0)
		{
			prompt = "input? ";
		}

		try
		{
			System.out.print(prompt);
			String input = in.readLine();
			if (input == null || input.length() == 0)
			{
				return def == null ? "" : def;
			}
			return input;
		}
		catch (Exception e)
		{
			System.out.println("getString() exception, returning empty string");
			return "";
		}
	}

	// Read a char from standard system input
	//
	public char getChar()
	{
		String s = getString();
		if (s.length() >= 1)
		{
			return s.charAt(0);
		}
		else
		{
			return '\n';
		}
	}

	// Read a Number as a String from standard system input
	// Return the Number
	//
	public Number getNumber()
	{
		String numberString = getString();
		try
		{
			numberString = numberString.trim().toUpperCase();
			return NumberFormat.getInstance().parse(numberString);
		}
		catch (Exception e)
		{
			// if any exception occurs, just give a 0 back
			//
			// ^^^
			// This is a dubious strategy, that was part of the original
			// implementation. Will need to consider a better approach.
			//
			out.println("getNumber() exception, returning 0");
			return new Integer(0);
		}
	}

	public int getInt()
	{
		return getNumber().intValue();
	}

	public float getFloat()
	{
		return getNumber().floatValue();
	}

	public double getDouble()
	{
		return getNumber().doubleValue();
	}

	private Integer attemptToRecognizeUserInputAsChoice(List<String> choices, String choice)
	{
		if (choices == null || choices.size() == 0)
		{
			return null;
		}

		if (choice == null || choice.length() == 0)
		{
			return null;
		}

		try
		{
			int i = Integer.parseInt(choice);
			if (i >= 0 || i < choices.size())
			{
				return i;
			}
		}
		catch (NumberFormatException e)
		{
			// Not an integer.
			// Fall through and try to match choice to one of the items in the list.
		}

		int pos = choices.indexOf(choice);
		if (pos == -1)
		{
			return null;
		}

		return pos;
	}


}
