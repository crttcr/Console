package console.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserInputProcessor
{
	private static String  SPLIT_TOKEN   = "##";
	private static Pattern SPLIT_PATTERN = Pattern.compile(SPLIT_TOKEN);

	private UserInputProcessor() {}
//	private static UserInputProcessor INSTANCE = new UserInputProcessor();
//	public  static UserInputProcessor getInstance() { return INSTANCE; }
	
	/**
	 * Input processing.
	 * 
	 * Input to a menu selection is a single string.  The string is decomposed and
	 * an appropriate action is returned.
	 * 
	 * with a 
	 * @param input
	 * @return
	 */
	
	public static UserInputObject[] processInput(String input)
	{
		// Null inputs receive null actions
		//
		if (input == null)
			return new UserInputObject[0];
		
		if (input.length() == 0)
			return new UserInputObject[0];

		String[] parts = separateCommands(input);
		
		List<UserInputObject> list = new ArrayList<UserInputObject>();
		for (int i = 0; i < parts.length; i++)
		{
			String    part = parts[i];
			
			if (part == null)
				continue;
			
			String    verb = part;
			String   param = null;
			int      index = part.indexOf(" ");
			if (index > 0)
			{
				verb  = part.substring(0, index);
				param = part.substring(index + 1, part.length());
				param = param.trim();
			}
			
			UserInputObject uc = new UserInputObject();
			uc.text  = verb;
			uc.parameter   = param;
			
			list.add(uc);
		}
		
		return (UserInputObject[]) list.toArray(new UserInputObject[0]);
	}
	
	/** 
	 * Converts a single string into a compound set of menu inputs.
	 * Always returns a valid array, even on bad inputs.
	 * 
	 * For example, the input
	 * 3 hello ## 4 ## x taste free
	 * 
	 * returns a 3 element array containing the strings: 	 "3 hello", "4", "x taste free"
	 * 
	 * @param input
	 * @return An array containing separated inputs
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
