package xivvic.console;

//
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.NumberFormat;

public class Stdin
{

	static InputStreamReader	converter	= new InputStreamReader(System.in);

	static BufferedReader		in				= new BufferedReader(converter);

	// Read a String from standard system input
	public static String getString()
	{
		try
		{
			return in.readLine();
		}
		catch (Exception e)
		{
			System.out.println("getString() exception, returning empty string");
			return "";
		}
	}
	
	public static String promptString(String prompt, String def)
	{
		if (prompt == null || prompt.length() == 0)
			prompt = "input? ";

		try
		{
			System.out.print(prompt);
			String input = in.readLine();
			if (input == null || input.length() == 0)
				return def == null ? "" : def;
			return input;
		}
		catch (Exception e)
		{
			System.out.println("getString() exception, returning empty string");
			return "";
		}
	}

	// Read a char from standard system input
	public static char getChar()
	{
		String s = getString();
		if (s.length() >= 1) return s.charAt(0);
		else
			return '\n';
	}

	// Read a Number as a String from standard system input
	// Return the Number
	public static Number getNumber()
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
			System.out.println("getNumber() exception, returning 0");
			return new Integer(0);
		}
	}

	// Read an integer from standard system input
	public static int getInt()
	{
		return getNumber().intValue();
	}

	// Read a float from standard system input
	public static float getFloat()
	{
		return getNumber().floatValue();
	}

	// Read a double from standard system input
	public static double getDouble()
	{
		return getNumber().doubleValue();
	}

}
