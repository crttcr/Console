package console.menu;

import java.io.PrintStream;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import xivvic.console.action.Action;
import xivvic.console.action.ActionBase;
import xivvic.console.action.ActionManager;
import xivvic.console.io.InputProcessor;
import xivvic.console.io.OpSpec;

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
	Deque<IMenu> stack = new LinkedList<IMenu>();
	
	// Singleton 
	//
	private MenuManager() {}
	private static MenuManager singleton = new MenuManager();
	public  static MenuManager getInstance() { return singleton; }

	
	/**
	 * Return a list of commands that are known to the menu system.
	 * 
	 * @return
	 */
	public String[] commands(String context)
	{
		ActionManager am = ActionManager.getInstance();
		
		return am.commands(context);
	}

	/**
	 * Reset the MenuManager to have only the given menu which will be the top and only level.
	 * 
	 * @param menu
	 */
	public void reset()
	{
		stack.clear();          // Empty menu stack
		ActionManager.getInstance().reset();
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

		ActionManager am = ActionManager.getInstance();
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
					popMenuStack();
				else
					stack.clear();
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
		ActionManager amg = ActionManager.getInstance();
		amg.register(action, null, text);
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
	public void addMenu(IMenu menu)
	{
		if (menu == null)
		{
			String s = "MenuManager.addMenu(null) no action taken.";
			System.err.println(s);
			return;
		}
		
		ActionManager amg = ActionManager.getInstance();
		
		String left = menu.getCode();
		int index = 0;
		Iterator<IMenuItem> it = menu.iterator();
		while (it.hasNext())
		{
			IMenuItem item = it.next();
			
			index++;

			amg.register(item, left, item.getShortcut());
			amg.register(item, left, Integer.toString(index));
		}
	}
	
	/** Invoke a menu choice
	 * 
	 * @param input text string representing the user's input at the menu prompt.
	 */
	public void invoke(String input)
	{
		IMenu   active = activeMenu();
		String context = active.getCode();
		OpSpec[]   ops = InputProcessor.processInput(context, input);

		for (OpSpec cmd : ops)
		{
			Action action = cmd.getAction();
			
			if (action == null)
				continue;
			
			String param = cmd.getParameter(); 
			action.invoke(param);
		}
	}

	
	private Action locateAction(String token)
	{
		// First attempt to match input is against the
		// current menu.
		//
		IMenu menu = activeMenu();
		if (menu == null)
			return null;
		
		ActionManager amg = ActionManager.getInstance();
		
		Action action = amg.command(menu.getCode(), token);
		
		if (action != null)
			return action;
		
		// If the current menu fails, try looking up the token in the
		// global context.
		//
		action = amg.command(null, token);
		return action;
	}
	
	void push(IMenu menu)
	{
		stack.add(menu);
	}

	
	void popMenuStack()
	{
		if (stack.size() > 0)
			stack.removeLast();
	}
	
	public String activeMenuPrefix()
	{
		IMenu menu = activeMenu();
		
		if (menu == null)
			return null;
		
		return menu.getCode();
	}

	private IMenu activeMenu()
	{
		if (stack.isEmpty())
			return null;
		
		return (IMenu) stack.getLast();
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
		IMenu   active = activeMenu();
		String context = active.getCode();
		
		OpSpec[] uio = InputProcessor.processInput(context, input);
		
		if (uio.length == 0)
			return false;
		
		OpSpec cmd = uio[0];
		if (cmd == null)
			return false;
		
		Action action = locateAction(cmd.getText());
		cmd.setAction(action);

		return action != null;
	}
	
	public void display(PrintStream out)
	{
		IMenu menu = activeMenu();
		String   s = MenuFormatter.format(menu, stack);
		
		out.println(s);
	}

	/**
	 * Make the specified menu the first and only menu on the stack.
	 * Nop if the menu is null.
	 * 
	 * @param menu
	 */
	public void addStartMenu(IMenu menu)
	{
		if (menu == null)
			return;

		addMenu(menu);
		
		stack.clear();
		push(menu);
	}
}
