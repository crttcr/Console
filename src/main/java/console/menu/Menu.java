package console.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xivvic.console.action.Action;
import xivvic.console.action.ActionBase;

public class Menu 
	implements IMenu
{
	/**
	 * Short, unique code representing the menu.  
	 * 
	 */
	private final String code;
	/**
	 * Items in the menu.  Each item consists of an MenuItem or an IMenu
	 * 
	 */
	private final MenuManager manager;
	/**
	 * Items in the menu.  Each item consists of an MenuItem or an IMenu
	 * 
	 */
	private List<IMenuItem>   items = new ArrayList<IMenuItem>();
	
	/**
	 * Short descriptive title for a menu
	 */
	private String title;
	
	/**
	 * Basic constructor
	 * @param _title Menu's title for display
	 */
	public Menu(String _title, String _code, MenuManager _manager)
	{
		manager = _manager;
		code    = _code  == null ? "Menu" : _code;
		title   = _title == null ? "Menu" : _title;
		
		if (manager == null)
			throw new IllegalArgumentException("Not expecting null menu manager");
	}

	
	/**
	 * Adds a MenuItem to the menu.  Returns <code>false</code> if the parameter
	 * supplied is null.  Items are maintained in entry order.
	 * 
	 * @param item to be added to the menu.  
	 * @return true if item was added successfully, false otherwise
	 */
	public boolean addItem(IMenuItem item)
	{
		if (item == null)
			return false;
		
		items.add(item);
		return true;
	}
	
	
	/**
	 * Adds a (Text, MenuAction) pair to the menu after wrapping them in an
	 * IMenuItem.  Returns <code>false</code> if the parameter
	 * supplied is null.  Items are maintained in entry order.
	 * 
	 * @param item to be added to the menu.  
	 * @return true if item was added successfully, false otherwise
	 */
	public boolean addItem(String text, String code, Action action)
	{
		if (text == null)
			return false;
		
		IMenuItem item = new MenuItem(text, code, action);
		return addItem(item);
	}

	/**
	 * Adds an IMenu as an IMenuItem for this object.  Note that this
	 * will register the menu with the MenuManger causing its commands
	 * to be added to the compile table.
	 * 
	 * @param menu
	 * @return
	 */
	public boolean addItem(final IMenu menu)
	{
		if (menu == null)
			return false;
		
		manager.addMenu(menu);
		
		final String description = "Choose a submenu and make it the active menu";
		final String title       = menu.getTitle();
		
		Action action = new ActionBase(title, description, true)
		{
			@Override
			public void internal_invoke(Object input) 
			{ 
				manager.push(menu); 
			}
		};
		
		String    code = menu.getCode();
		IMenuItem item = new MenuItem(title, code, action);
		
		return addItem(item);
	}
	
	public String getCode()
	{
		return code;
	}

	
	public String getTitle()
	{
		return title;
	}
	
	/* (non-Javadoc)
	 * @see console.menu.IMenu#isValidChoice(int)
	 */
	public boolean isValidChoice(int id)
	{
		if (id >= 0 && id < items.size())
			return true;
		
		return false;
	}
	
	public boolean isValidChoice(String s)
	{
		if (s == null)
			return false;

		Iterator<IMenuItem> it = items.iterator();
		while (it.hasNext())
		{
			IMenuItem item = it.next();
			String shortcut = item.getShortcut();
			if (shortcut != null && shortcut.equals(s))
				return true;
		}
		return false;
	}

	
	@Override
	public String toString()
	{
		return getTitle();
	}
	
	public Iterator<IMenuItem> iterator()
	{
		return items.iterator();
	}

	public int getCount()
	{
		return items.size();
	}
	
	
	/** Returns the specified item.  NOTE:  1-based indexing!!!
	 * 
	 */
	public IMenuItem getItem(int index)
	{
		IMenuItem item = (IMenuItem) items.get(index - 1);
		return item;
	}
}
