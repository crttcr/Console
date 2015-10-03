package xivvic.console.input;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for processing an integer input
 * 
 * @author Reid
 *
 */
public class InputProcessorSingleInteger
	extends InputProcessorBase
{
	public static final String KEY = "aipsi.key";
	
	private boolean isRequired = false;
	private Integer defaultValue;
	
	
	public InputProcessorSingleInteger(boolean required)
	{
		super();
		this.isRequired = required;
	}
	
	public InputProcessorSingleInteger(int defaultValue)
	{
		super();
		this.defaultValue = defaultValue;
	}

	@Override
	public Map<String, Object> process(Object params)
	{
		if (params == null)
		{
			if (! isRequired)
				return result;
			else
				return null;
		}
		
		if (! (params instanceof String))
		{
			if (isRequired)
				return new HashMap<String, Object>();
			else
				return null;
		}
		
		String s = (String) params;
		return process(s);
	}

	@Override
	public Map<String, Object> process(String params)
	{
		if (params == null)
		{
			if (defaultValue != null)
			{
				result.put(KEY, defaultValue);
				return result;
			}

			if (! isRequired)
				return result;
			else
				return null;
		}
		
		String s = params.trim();
		
		try
		{
			int i = Integer.parseInt(s);
			result.put(KEY, i);
		}
		catch (NumberFormatException e)
		{
			if (defaultValue != null)
				result.put(KEY, defaultValue);
			else
			{
				if (isRequired)
					return null;
			}
		}
		
//		// Run post actions
//		//
//		for (Runnable r : postActions)
//			r.run();
		
		return result;
	}

}
