package xivvic.console.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The ActionManager contains references to all the actions that the application can
 * perform.
 *
 * Actions can be special actions that the application performs at various points in
 * the lifecycle, or they can be normal actions that are invoked as a result of user
 * selection. The same action can be registered as both special and as normal.
 *
 * Special actions are registered with an ActionTiming value that indicates when the
 * action should be performed.
 *
 * Normal actions are registered with a context that represents a currently selected
 * menu so that two actions named "list" can perform two different tasks based on
 * being part of different menus.
 *
 * ActionManager maintains normal commands/actions as both both local and global flavors.
 * Commands are associated with a name and a context.  If the context is null,
 * it is assumed to be a "global context"
 *
 * @author reid
 */
public class ActionManager
{
	public static final String	GLOBAL_CONTEXT	= "";
	public static final String	DELIMITER	   = ".";

	private final Set<String>         contexts = new HashSet<>();
	private final Map<String, Action> commands = new HashMap<>();
	private final Map<ActionTiming, List<Action>> specials = new HashMap<>();

	public ActionManager() {}

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
	 * If the context is null or the empty string, it returns global commands.
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

		if (context == null || context.length() == 0)
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

	/**
	 * Return a sorted list of all commands.
	 *
	 * @return
	 */
	public String[] commands()
	{
		Set<String> keys = null;
		synchronized(this)
		{
			keys = commands.keySet();
		}

		String[] rv = keys.toArray(new String[0]);
		Arrays.sort(rv);
		return rv;
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

	/**
	 * Registers a "normal" command, meant be invoked by a menu action.
	 *
	 * @param action the action to register
	 * @param context the context for action lookup (e.g. a menu location), or null for the global context
	 * @param aliases a list of alternative names for the action e.g. "bye", "quit", "exit"
	 */
	public void register(Action action, String context, String... aliases)
	{
		Objects.requireNonNull(action);

		context = context == null ? GLOBAL_CONTEXT : context;

		String name = action.name();
		String  key = makekey(context, name);

		synchronized (this)
		{
			contexts.add(context);
			commands.put(key, action);
		}

		for (String alias: aliases)
		{
			String akey = makekey(context, alias);

			synchronized (this)
			{
				contexts.add(context);
				commands.put(akey, action);
			}
		}
	}

	/**
	 * Registers a "normal" command, meant be invoked by a menu action.
	 *
	 * @param action the action to register
	 * @param context the context for action lookup (e.g. a menu location), or null for the global context
	 * @param aliases a list of alternative names for the action e.g. "bye", "quit", "exit"
	 */
	public void register(Action action, ActionTiming timing)
	{
		Objects.requireNonNull(action);
		Objects.requireNonNull(timing);

		String name = action.name();
		String  key = makekey(timing.name(), name);

		synchronized (this)
		{
			commands.put(key, action);
			List<Action> list = specials.getOrDefault(timing, new ArrayList<Action>());
			list.add(action);
			specials.put(timing, list);
		}
	}

	public List<Action> getActionsFor(ActionTiming timing)
	{
		if (timing == null)
		{
			return Collections.emptyList();
		}

		List<Action> list = specials.getOrDefault(timing, new ArrayList<Action>());
		return Collections.unmodifiableList(list);
	}

	private String makekey(String left, String right)
	{
		if (left == null)
		{
			left = "";
		}
		else
		{
			left = left.toLowerCase();
		}

		if (right == null)
		{
			right = "";
		}
		else
		{
			right = right.toLowerCase();
		}

		return left + DELIMITER + right;
	}

	private String[] matchingStrings(Set<String> set, String match)
	{
		List<String> list = new ArrayList<>();

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
}
