package console.menu;

import java.util.Iterator;

public interface IMenu
	extends Iterable<IMenuItem>
{
	/** 
	 * Text to be displayed at the top of the menu
	 * 
	 */
	public abstract String getTitle();
	
	/** 
	 * Unique code that represents the menu.  This can be used in combination with a
	 * menu item's code to create a unique key for the command.  
	 * 
	 */
	public abstract String  getCode();

	/** 
	 * How many items are in the menu.
	 * 
	 */
	public abstract int    getCount();

	
	/** 
	 * Retrieve an item by index.  In this case, items are indexed by their
	 * position in a menu.  Thus the first menu item, will have id == 1, not 0.
	 * 
	 * The 0'th item is reserved for an action representing exiting the menu.
	 * 
	 */
	public abstract IMenuItem getItem(int id);

	/**
	 * Is this a valid menu item number?
	 * Returns true if "id" is 0.
	 * Returns true if "id" is in the range of the number of items in the menu. 
	 */
	public abstract boolean isValidChoice(int id);

	/**
	 * Is this a valid string to select an item from the current menu?
	 * Returns false if "t" is null.
	 * Returns true if this menu contains a MenuItem mi where mi.getCode().equals(t);
	 * 
	 */
	public abstract boolean isValidChoice(String t);
	
	/**
	 * Returns an iterator for sequential access to the menu's items.
	 */
	public Iterator<IMenuItem> iterator();
}