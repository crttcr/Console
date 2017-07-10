package xivvic.console.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import xivvic.console.action.Action;
import xivvic.console.action.ActionBase;

@Data
@Accessors(fluent = true)
public class Menu
{
	/**
	 * Short, unique code representing the menu.
	 *
	 */
	private final String code;

	/**
	 * Unique code that represents the menu.  This can be used in combination with a
	 * menu item's code to create a unique key for the command.
	 */
	private final MenuManager manager;

	/**
	 * Items in the menu.  Each item consists of an MenuItem or an Menu
	 *
	 */
	private List<MenuItem>   items = new ArrayList<>();

	/**
	 * Text to be displayed at the top of the menu
	 *
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
		{
			throw new IllegalArgumentException("Not expecting null menu manager");
		}
	}


	/**
	 * Adds a MenuItem to the menu.  Returns <code>false</code> if the parameter
	 * supplied is null.  Items are maintained in entry order.
	 *
	 * @param item to be added to the menu.
	 * @return true if item was added successfully, false otherwise
	 */
	public boolean addItem(MenuItem item)
	{
		if (item == null)
		{
			return false;
		}

		items.add(item);
		return true;
	}


	/**
	 * Adds a (Text, MenuAction) pair to the menu after wrapping them in an
	 * MenuItem.  Returns <code>false</code> if the parameter
	 * supplied is null.  Items are maintained in entry order.
	 *
	 * @param item to be added to the menu.
	 * @return true if item was added successfully, false otherwise
	 */
	public boolean addItem(String text, String code, Action action)
	{
		if (text == null)
		{
			return false;
		}

		MenuItem item = new MenuItem(text, code, action);
		return addItem(item);
	}

	/**
	 * Adds an Menu as an IMenuItem for this object.  Note that this
	 * will register the menu with the MenuManger causing its commands
	 * to be added to the compile table.
	 *
	 * @param menu
	 * @return
	 */
	public boolean addItem(final Menu menu)
	{
		if (menu == null)
		{
			return false;
		}

		manager.addMenu(menu);

		final String description = "Choose a submenu and make it the active menu";
		final String title       = menu.title();

		Action action = new ActionBase(title, description, true)
		{
			@Override
			public void internal_invoke(Object input)
			{
				manager.push(menu);
			}
		};

		String    code = menu.code();
		MenuItem item = new MenuItem(title, code, action);

		return addItem(item);
	}

	/**
	 * Is this a valid menu item number?
	 * Returns true if "id" is 0.
	 * Returns true if "id" is in the range of the number of items in the menu.
	 */
	public boolean isValidChoice(int id)
	{
		if (id  <            0) { return false; }
		if (id >= items.size()) { return false; }

		return true;
	}

	/**
	 * Is this a valid string to select an item from the current menu?
	 * Returns false if "t" is null.
	 * Returns true if this menu contains a MenuItem mi where mi.getCode().equals(t);
	 *
	 */
	public boolean isValidChoice(String s)
	{
		if (s == null)
		{
			return false;
		}

		Iterator<MenuItem> it = items.iterator();
		while (it.hasNext())
		{
			MenuItem item = it.next();
			String shortcut = item.shortcut();
			if (shortcut != null && shortcut.equals(s))
			{
				return true;
			}
		}
		return false;
	}


	@Override
	public String toString()
	{
		return title();
	}

	public Iterator<MenuItem> iterator()
	{
		return items.iterator();
	}

	/**
	 * How many items are in the menu.
	 *
	 */
	public int count()
	{
		return items.size();
	}


	/**
	 * Retrieve an item by index.  In this case, items are indexed by their
	 * position in a menu.  Thus the first menu item, will have id == 1, not 0.
	 *
	 * The 0'th item is reserved for an action representing exiting the menu.
	 *
	 * NOTE: 1 based indexing.
	 *
	 */
	public MenuItem getItem(int index)
	{
		MenuItem item = items.get(index - 1);
		return item;
	}
}
