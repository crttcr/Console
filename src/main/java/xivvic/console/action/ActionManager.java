package xivvic.console.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ActionManager maintains application commands/actions, both local and global.
 * Commands are associated with a name and a context.  If the context is null,
 * it is assumed to be a "global context"
 * 
 * @author cturner
 */
public class ActionManager 
{
	public static final String	GLOBAL_CONTEXT	= "";
	public static final String	DELIMITER	   = ".";
	
	private final Set<String>         contexts = new HashSet<String>();
	private final Map<String, Action> commands = new HashMap<String, Action>();
	
	// Singleton 
	//
	private ActionManager() {}
	private static final ActionManager singleton = new ActionManager();

	public  static ActionManager getInstance() { return singleton; }

	
	/**
	 * Return an array of all contexts known to the action manager.
	 * 
	 * @return Array of all contexts.
	 */
	public String[] contexts()
	{
		String[] result = new String[0];
		
		synchronized(this)
		{
			result = contexts.toArray(result);;
		}
		
		Arrays.sort(result);
		return result;
	}
	
	/**
	 * Return a sorted list of commands for a specified context.
	 * If the context is null, this returns all commands.
	 * If the context is the empty string, it returns global commands.
	 * 
	 * @return
	 */
	public String[] commands(String context)
	{
		Set<String> keys = null;
		synchronized(this)
		{
			keys = commands.keySet();
		}
		
		String[] rv = null;
		
		if (context == null)
		{
			rv = keys.toArray(new String[0]);
		}
		else if (context.length() == 0)
		{
			rv = matchingStrings(keys, DELIMITER);
			
		}
		else
		{
			rv = matchingStrings(keys, context + DELIMITER);
		}
		
		Arrays.sort(rv);
		return rv;
	}
	
	private String[] matchingStrings(Set<String> set, String match)
	{
		List<String> list = new ArrayList<String>();
		
		for (String key : set)
		{
			if (key.startsWith(match))
			{
				String cmd = key.substring(match.length());
				list.add(cmd);
			}
		}
		String[] rv = list.toArray(new String[0]);
		
		return rv;
	}
	
	/**
	 * Reset the ActionManager to have only the given menu which will be the top and only level.
	 * 
	 * @param menu
	 */
	public synchronized void reset()
	{
		commands.clear();       // Remove all commands items
	}
	
	private String makekey(String left, String right)
	{
		if (left == null)
			left = "";
		else
			left = left.toLowerCase();
		
		if (right == null)
			right = "";
		else
			right = right.toLowerCase();
		
		return left + DELIMITER + right;
	}

	public Action command(String context, String name)
	{
		String key = makekey(context, name);
		Action  rv = null;
		
		synchronized (this)
		{
			rv = commands.get(key);
		}
		
		return rv;
	}
	
	public Action register(Action action, String context, String name)
	{
		if (name == null)
			return null;
		
		if (context == null)
			context = GLOBAL_CONTEXT;
		
		Action  rv = null;
		String key = makekey(context, name);
		
		synchronized (this)
		{
			contexts.add(context);
			rv = commands.get(key);
			commands.put(key, action);
		}
		
		return rv;
	}
}
