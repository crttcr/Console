/* FILE:      TextFormatter.java
 * CREATED:   Nov 8, 2003
 * CREATOR:   reid
 *
 * Copyright (c) 2003 Emroo.com
 * 
 */
package xivvic.console.util;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TextFormatter provides a variety of methods that are useful for producing
 * specific text transformations, such as an elipsis and fixed size substrings.  
 */

public class TextFormatter
{
	private TextFormatter() {}
	
	//	Static methods
	//
	/** boundedSubstrings breaks an input string, the text, into an array
	 * of substrings, each of which have a length less than or equal to the 
	 * length argument.
	 * 
	 * If length < 1, this 
	 * 
	 * @throws IllegalArgumentException when length < 1
	 * 
	 */
	public static String[] boundedSubstrings(String text, int length)
	{
		if (text == null)
			return new String[0];
		
		if (length < 1)
			throw new IllegalArgumentException("length must be a positive integer.");
		
		text = text.replaceAll("\n", " ");
		if (text.length() <= length)
		{	
			String[] array = new String[1];
			array[0] = text;
			return array;
		}
		
		List<String> list = new ArrayList<String>();
		BreakIterator  it = BreakIterator.getLineInstance();
		it.setText(text);
		
		int start = it.first();
		int end   = it.preceding(length);
		
		while (end != BreakIterator.DONE)
		{
			String current = text.substring(start, end);
			list.add(current);
			
			start = end;
			if (start + length >= text.length())
			{
				list.add(text.substring(end));
				end = BreakIterator.DONE;
			}
			else
			{
				end = it.preceding(start + length);
			}
		}
		
		return (String[]) list.toArray(new String[0]);
	}

	/** This method produces a substring of the given string that is suitable for putting
	 * into a fixed length display.  If the string is shorter than the desired length, it is
	 * returned in its entirety.  If it is too long, for the indicated length,
	 * this method returns a substring with three elipsis characters at the end.
	 * 
	 * @param String to receive the formatting.
	 * @param length of the returned string
	 * @param the character used to add elispses to long strings.
	 *  
	 * @return the formatted string with the indicated width.
	 * 
	 * @throws IllegalArgumentException if the length parameter is negative.
	 */
	public static String boundedElipsisSubstring(String string, int length, char elipsisChar)
	{
		if (string == null)
			return "";
		
		if (string.length() <= length)
			return string;
		
		if (length  < 0) 
			throw new IllegalArgumentException("length < 0 does not make sense for this function.");

		StringBuffer sb = new StringBuffer();
		if (length < 4)
		{
			for (int i = 0; i < length; i++)
			{
				sb.append(elipsisChar);
			}
			
			return sb.toString();
		}
		
		sb.append(string.substring(0, length - 3));
		sb.append(elipsisChar);
		sb.append(elipsisChar);
		sb.append(elipsisChar);

		return sb.toString();
	}
	

	/**
	 * Pad takes a String and returns a similar String padded to the specified
	 * length with a padding character.
	 *  
	 * @param s The input string
	 * @param length how long the result string should be
	 * @param pad the character with which padding is to be done.
	 * 
	 * @return a padded version of the input String
	 * 
	 * @throws IllegalArgumentException if the length parameter is negative.
	 */
	public static String pad(String s, int length, char pad)
	{
		if (length < 0)
			throw new IllegalArgumentException("pad(..., " + length + ", ...) cannot handle negative length");
		
		if (s == null)
			s = "";
		
		StringBuffer sb = new StringBuffer(s);
		
		for (int i = s.length(); i < length; i++)
			sb.append(pad);
		
		return sb.toString();
	}

	/**
	 * lpad takes a String and returns a similar String padded [on the left] to the specified
	 * length with a padding character.
	 *  
	 * @param s The input string
	 * @param length how long the result string should be
	 * @param pad the character with which padding is to be done.
	 * 
	 * @return a padded version of the input String
	 * 
	 * @throws IllegalArgumentException if the length parameter is negative.
	 */
	public static String padLeft(String s, int length, char pad)
	{
		if (length < 0)
			throw new IllegalArgumentException("pad(..., " + length + ", ...) cannot handle negative length");
		
		if (s == null)
			s = "";
		
		StringBuffer sb = new StringBuffer(s);
		
		for (int i = s.length(); i < length; i++)
			sb.insert(0, pad);
		
		return sb.toString();
	}

	
	/**
	 * join() combines an array of strings into a single string, with an
	 * optional separator between each.
	 * 
	 * @param array of strings to be joined together into a single string.
	 * @param separator is placed between each pair of strings.
	 * @return
	 */
	public static String join(String[] strings, String separator)
	{
		if (strings == null)
			return "";
		
		StringBuffer sb = new StringBuffer();
		String      sep = separator == null ? "" : separator;
		
		for (int i = 0; i < strings.length; i++)
		{
			sb.append(strings[i]);
			if (i < strings.length - 1)
				sb.append(sep);
		}
		
		return sb.toString();
	}


	/**
	 * coalesceWhitespace() converts all continguous ranges of whitespace characters
	 * into a single space, this includes converting tabs and newlines to spaces.
	 * If the input is null or an empty string, the result is an empty string.
	 * 
	 * @param text to be modified
	 * @return string that has consecutive whitespace characters collapsed into single spaces.
	 */
	public static String coalesceWhitespace(String text)
	{
		if (text == null || text.length() == 0)
			return "";
		
		Pattern pattern = Pattern.compile("\\s+");
		Matcher matcher = pattern.matcher(text);
		return matcher.replaceAll(" ");
	}


	/**
	 * Create a string that is capped on the edges by an "edge" character
	 * and filled with "interior" character.  The following declarations produce
	 * identical strings.  Of course, the length of "s" can be determined at runtime.
	 * When the interiorLength parameter is not a positive number, the resulting string
	 * will consist of two "edge" characters.
	 * 
	 * <code>
	 * String s = edgedLine('*', '-', 10);
	 * String t =  "*----------*";
	 * assert (s.equals(t));
	 * </code>
	 * 
	 * @param edge the character to place on the edges of the result string
	 * @param interior the character to place in the interior of the result string
	 * @param interiorLength how many characters should be inserted into the middle
	 * @return a string as described
	 */
	public static String edgedLine(char edge, char interior, int interiorLength)
	{
		int count = interiorLength > 0 ? interiorLength : 0;
		
		StringBuffer sb = new StringBuffer(count + 2);
		sb.append(edge);
		if (count > 0)
		{
			for (int i = 0; i < count; i++)
				sb.append(interior);
		}
		
		sb.append(edge);
		return sb.toString();
	}
	
}
