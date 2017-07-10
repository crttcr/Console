/* FILE:      ConsoleUtils.java
 * CREATED:   Jan 21, 2006
 * CREATOR:   reid
 *
 * Copyright (c) 2005 Xivvic.com
 */
package xivvic.console.app;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import xivvic.console.interact.Stdin;


public class ConsoleUtils
{
	public static final int      NO_SELECTION = -1;
	public static final String        NEWLINE = System.getProperty("line.separator");

	public static int userSelect(List<Object> list)
	{
		if (list == null || list.size() == 0)
		{
			return NO_SELECTION;
		}

		int index = 1;

		Iterator<Object> it = list.iterator();
		while (it.hasNext())
		{
			Object o = it.next();
			System.out.print(index);
			System.out.print(": ");
			System.out.println(o.toString());
			index++;
		}

		System.out.println("Choose from the list above by #");
		int choice = Stdin.getInt();
		if (choice == 0 || choice > list.size())
		{
			return NO_SELECTION;
		}

		return choice - 1;
	}

	/**
	 * Prompt user for a series of integers, read the result from the command line, and
	 * return an array of the result.
	 *
	 * If there are no integers in the user's response, returns a zero length array.
	 *
	 * @param prompt
	 * @return
	 */

	public static int[] userInts(String prompt)
	{
		System.out.println(prompt);
		String s = Stdin.getString();

		if (s == null || s.length() == 0)
		{
			return new int[0];
		}


		// Look for a digit in the string.  If one is found it can be processed.
		// If not, then an empty array is returned.
		//
		boolean hasDigit = false;
		for (int i = 0; i < s.length(); i++)
		{
			if (Character.isDigit(s.charAt(i)))
			{
				hasDigit = true;
				break;
			}
		}

		if (! hasDigit)
		{
			return new int[0];
		}

		// A digit was found.  Split on a pattern that matches anything that's not an integer.
		//
		String[] values = s.split("[^0-9-]+");
		int[]    result = new int[values.length];

		for (int i = 0; i <  values.length; i++)
		{
			result[i] = Integer.parseInt(values[i]);
		}

		return result;
	}


	public static int userSelect(Object[] array)
	{
		if (array == null || array.length == 0)
		{
			return NO_SELECTION;
		}

		for (int i = 0; i < array.length; i++)
		{
			Object object = array[i];
			System.out.print(i + 1);
			System.out.print(": ");
			System.out.println(object.toString());
		}

		System.out.println("Choose from the list above by #");
		int choice = Stdin.getInt();
		if (choice == 0 || choice > array.length)
		{
			return NO_SELECTION;
		}

		return choice - 1;
	}


	public static void display(Writer out, Object[] array)
	throws IOException
	{
		if (array.length == 0)
		{
			out.write("Empty");
			out.write(NEWLINE);
		}

		for (int i = 0; i < array.length; i++)
		{
			Object o = array[i];
			out.write(o == null ? "<null>" : o.toString());
			out.write(NEWLINE);
		}
		out.flush();
	}

	public static void display(Writer out, List<Object> list)
	throws IOException
	{
		if (list.size() == 0)
		{
			out.write("Empty");
			out.write(NEWLINE);
		}

		Iterator<Object> it = list.iterator();
		while (it.hasNext())
		{
			Object o = it.next();
			out.write(o == null ? "<null>" : o.toString());
			out.write(NEWLINE);
		}
		out.flush();
	}

}
