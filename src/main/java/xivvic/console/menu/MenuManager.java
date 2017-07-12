package xivvic.console.menu;

import java.io.PrintStream;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import xivvic.console.action.Action;
import xivvic.console.action.ActionBase;
import xivvic.console.action.ActionManager;
import xivvic.console.interact.InputProcessor;
import xivvic.console.interact.OpSpec;

/**
 * MenuManager maintains an activation stack menus.
 *
 * @author cturner
 */
public class MenuManager
{
	/**
	 * Stack of menus, starting with the main menu.  As each
	 * sub menu is invoked, it gets pushed on the stack and becomes
	 * the active menu.
	 *
	 */
	private Deque<Menu> stack = new LinkedList<>();
	private final ActionManager am;

	public MenuManager(ActionManager am)
	{
		this.am = am;
	}

	/**
	 * Return a list of commands that are known to the menu system.
	 *
	 * @return
	 */
	public String[] commands(String context)
	{
		return am.commands(context);
	}

	public void addCoreActions()
	{
		String name        = "exit";
		String description = "Exit the application";

		Action action = new ActionBase(name, description, true)
		{
			@Override
			public void internal_invoke(Object param)
			{
				stack.clear();
			}
		};

		am.register(action, null, "bye");
		am.register(action, null, "exit");
		am.register(action, null, "quit");

		name        = "up";
		description = "Go to a higher level menu";
		action = new ActionBase(name, description, true)
		{
			@Override
			public void internal_invoke(Object param)
			{
				if (stack.size() > 1)
				{
					popMenuStack();
				}
				else
				{
					stack.clear();
				}
			}
		};


		am.register(action, null, "0");
		am.register(action, null, "up");
	}

	/**
	 * Adds a command to the menu system without a menu context.  It is
	 * hidden in the sense that it will never be seen when a menu is displayed.
	 * "exit" is an example.
	 *
	 * @param text the string that invokes the action
	 * @param action action to invoke
	 */
	public void addHiddenAction(String text, Action action)
	{
		am.register(action, null, text);
	}

	/**
	 * Processing required to add a menu.  Essentially the menu's actions
	 * are mapped in the commands table
	 *
	 * menu.code x menuitem.index -> menuitem
	 * menu.code x menuitem.code  -> menuitem
	 *
	 * @param menu the menu that is to be handled by this manager.
	 */
	public void addMenu(Menu menu)
	{
		if (menu == null)
		{
			String s = "MenuManager.addMenu(null) no action taken.";
			System.err.println(s);
			return;
		}

		String left = menu.code();
		int index = 0;
		Iterator<MenuItem> it = menu.iterator();
		while (it.hasNext())
		{
			MenuItem item = it.next();

			index++;

			am.register(item.action(), left, item.shortcut());
			am.register(item.action(), left, Integer.toString(index));
		}
	}

	/** Invoke a menu choice
	 *
	 * @param input text string representing the user's input at the menu prompt.
	 */
	public void invoke(String input)
	{
		Menu   active = activeMenu();
		String context = active.code();
		OpSpec[]   ops = InputProcessor.processInput(context, input, am);

		for (OpSpec cmd : ops)
		{
			Action action = cmd.action();

			if (action == null)
			{
				continue;
			}

			String param = cmd.parameter();
			action.invoke(param);
		}
	}


	private Action locateAction(String token)
	{
		// First attempt to match input is against the
		// current menu.
		//
		Menu menu = activeMenu();
		if (menu == null)
		{
			return null;
		}

		Action action = am.command(menu.code(), token);

		if (action != null)
		{
			return action;
		}

		// If the current menu fails, try looking up the token in the
		// global context.
		//
		action = am.command(null, token);
		return action;
	}

	void push(Menu menu)
	{
		stack.add(menu);
	}


	void popMenuStack()
	{
		if (stack.size() > 0)
		{
			stack.removeLast();
		}
	}

	public String activeMenuPrefix()
	{
		Menu menu = activeMenu();

		if (menu == null)
		{
			return null;
		}

		return menu.code();
	}

	private Menu activeMenu()
	{
		if (stack.isEmpty())
		{
			return null;
		}

		return stack.getLast();
	}

	public boolean isDone()
	{
		return stack.isEmpty();
	}

	/**
	 * Determine if a menu input is valid.  Note that in the case of
	 * a compound input (i.e. "1 ## 4") then only the first command is
	 * validated.
	 *
	 * @param input text to be validated
	 * @return true if it represents a good command for this MenuManager
	 */
	public boolean isValidSelection(String input)
	{
		Menu   active = activeMenu();
		String context = active.code();

		OpSpec[] uio = InputProcessor.processInput(context, input, am);

		if (uio.length == 0)
		{
			return false;
		}

		OpSpec cmd = uio[0];
		if (cmd == null)
		{
			return false;
		}

		Action action = locateAction(cmd.text());
		cmd.action(action);

		return action != null;
	}

	public void display(PrintStream out)
	{
		Menu menu = activeMenu();
		String   s = MenuFormatter.format(menu, stack);

		out.println(s);
	}

	/**
	 * Make the specified menu the first and only menu on the stack.
	 * Nop if the menu is null.
	 *
	 * @param menu
	 */
	public void addStartMenu(Menu menu)
	{
		if (menu == null)
		{
			return;
		}

		addMenu(menu);

		stack.clear();
		push(menu);
	}
}
