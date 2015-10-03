package xivvic.console.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtil
{
	/** 
	 * This expression matches all the major ASCII newline conventions
	 * See:  http://en.wikipedia.org/wiki/Newline for details
	 * 
	 *  \n   Decimal 10    -- 0x0A       -- LineFeed         --  Unix
	 *  \r   Decimal 13    -- 0x0D       -- Carriage Return  --  Commodore, Mac OS, Apple II, 0S-9
	 *  \r\n Decimal 13 10 -- 0x0D 0x0A  -- CRLF             --  Brain-dead Windows
	 */
	public static final String REGEX_GENERIC_LINE_END = "\\n|\\r|\\r\\n";
	
	//	Static methods
	//
	/** 
	 * findMatches looks for the substring "sub" in the array of strings
	 * and returns an array of those containing the substring.  Returns an
	 * empty array if none are found or if either argument is blank.
	 * 
	 */
	
	public static String[] findMatches(String sub, String[] array)
	{
		if (sub == null || sub.length() == 0)
			return new String[0];
		
		if (array == null || array.length == 0)
			return new String[0];


		List<String> list = new LinkedList<String>();
		for (String item : array)
		{
			if (! item.contains(sub))
				continue;
			
			list.add(item);
		}
		
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * Process a String array separating the contents into groups based on the value
	 * of a prefix.  Given the following input
	 * 
	 * String input[] = { "", "apple", "car:make", "house:rooms", "house:address", "car:color", "car:model" }
	 * 
	 * List<String[]> groups = StringUtil.groupByPrefix(input, ":");
	 * 
	 * will return a list of the following arrays 
	 * 
	 * { "", "apple" }
	 * { "car:make", "car:color", "car:model" }
	 * { "house:rooms", "house:address" }
	 * 
	 * @param commands
	 * @param string
	 * @return
	 */
	public static List<String[]> groupByPrefix(String[] input, String string)
	{
		List<String[]> result = new LinkedList<String[]>();
		
		if (input == null || input.length == 0)
			return result;
		
		if (string == null || string.length() == 0)
			return result;
		
		Map<String, List<String>> groups = new HashMap<String, List<String>>();
		
		for (String item : input)
		{
			String key = "";
			int  index = item.indexOf(string);
			if (index != -1)
				key = item.substring(0, index);
			
			List<String> bucket = groups.get(key);
			if (bucket == null)
			{
				bucket = new LinkedList<String>();
				groups.put(key, bucket);
			}
			
			bucket.add(item);
		}
		
		Set<String>  prefixes = groups.keySet();
		Iterator<String> it = prefixes.iterator();
		while (it.hasNext())
		{
			String       prefix = it.next();
			List<String> values = groups.get(prefix);
			String[] strings = new String[values.size()];
			int i = 0;
			for (String s : values)
				strings[i++] = s;
			
			result.add(strings);
		}
			
		return result;
	}
	
	/**
	 * Convert a String into an input stream.  Uses the optional encoding.  If the encoding
	 * is null then, it uses "UTF-8" encoding.  Handles null text input by converting it to
	 * the empty string.  This should produce an InputStream that immediately returns end of
	 * buffer.
	 * 
	 * 
	 * @param text that is to be placed in the input stream
	 * @param encoding that will be used to convert the String's bytes
	 * @return
	 */
	// Would have to handle encoding and errors before putting it into a library function.
	//
	public static InputStream convertStringToInputStream(String text, String encoding)
	{
		if (text == null)
			text = "";
		
		if (encoding == null)
			encoding = "UTF-8";
		
		InputStream is = null;
		try
		{
			byte[] bytes = text.getBytes(encoding);
			is = new ByteArrayInputStream(bytes);
		}
		catch (Exception e)
		{
			byte[] empty = new byte[0];
			is = new ByteArrayInputStream(empty);
		}
		
		return is;
	}


}
