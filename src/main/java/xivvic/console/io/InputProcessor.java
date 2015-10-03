package xivvic.console.io;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import xivvic.console.action.Action;
import xivvic.console.action.ActionManager;

public class InputProcessor
{
	private static final String  SPACE         = " ";
	private static final String  SPLIT_TOKEN   = "##";
	private static final Pattern SPLIT_PATTERN = Pattern.compile(SPLIT_TOKEN);

	// @Threadsafe
	// Static only class.
	// No mutable state, but requires ActionManager to be threadsafe.
	//
	private InputProcessor() {}
	
	/**
	 * Input processing returns an array of OpSpec object representing the decoded
	 * commands.  An array is returned because the string input can represent multiple
	 * commands if they're separated by the {@see SPLIT_TOKEN}.  All commands need to
	 * be found for this method to return an array of length greater than zero.
	 * 
	 * Input to a menu selection is a single string.  The string is split and
	 * an appropriate action is returned.
	 * 
	 * @param context an optional context specification for processing a command.
	 * @param input the input to process with parameter and Action identified
	 * @return
	 */
	
	public static OpSpec[] processInput(String context, String input)
	{
		if (input == null || input.length() == 0)
			return new OpSpec[0];

		ActionManager  am = ActionManager.getInstance();
		List<OpSpec> list = new ArrayList<OpSpec>();
		String[]    parts = separateCommands(input);
		
		for (String part : parts)
		{
			if (part == null)
				continue;
			
			String    verb = part.trim();
			String   param = null;
			int      index = part.indexOf(SPACE);
			if (index > 0)
			{
				verb  = part.substring(0, index);
				param = part.substring(index + 1);
				param = param.trim();
			}
			
			int del = verb.indexOf(ActionManager.DELIMITER);
			
			if (del != -1)
			{
				context = verb.substring(0, del);
				verb    = verb.substring(del + 1);
			}
			
			Action action = am.command(context, verb);
			
			if (action == null)
			{
				action = am.command(ActionManager.GLOBAL_CONTEXT, verb);
				
				if (action == null)
					return new OpSpec[0];
			}
			
			OpSpec os = new OpSpec(verb, param);
			os.setAction(action);
			
			list.add(os);
		}
		
		return (OpSpec[]) list.toArray(new OpSpec[0]);
	}
	
	/** 
	 * Converts a single string into a compound set of menu inputs.
	 * Always returns a valid array, even on bad inputs.  After splitting
	 * on the 
	 * 
	 * For example, the input
	 * 2 hello ## 4 ## x taste free
	 * 
	 * returns a 3 element array containing the strings: 	 "2 hello", "4", "x taste free"
	 * 
	 * @param input
	 * @return An array containing separated inputs, or zero length array on error.
	 */
	private static String[] separateCommands(String input)
	{
		if (input == null)
			return new String[0];
		
		if (input.length() == 0)
			return new String[0];
		
		if (input.indexOf(SPLIT_TOKEN) == -1)
		{
			String[] rv = new String[1];
			rv[0] = input.trim();
			return rv;
		}

	   String[] items = SPLIT_PATTERN.split(input);
	   for (int i = 0; i < items.length; i++)
		{
	   	if (items[i] == null)
	   		continue;
	   	
			items[i] = items[i].trim();
		}
	   
		return items;
	}

}
