package console.menu;

import xivvic.console.action.Action;

public class MenuItem 
	implements IMenuItem
{
	private final String     text;
	private final String shortcut;
	private final Action   action;
	
	public MenuItem(String text, String shortcut, Action action)
	{
		if (text == null || text.length() == 0)
			throw new IllegalArgumentException("MenuItem constructor requires valid text");
		
		this.text     = text;
		this.action   = action;
		this.shortcut = shortcut;
	}
	
	public String getText()
	{
		return text;
	}
	
	public String getShortcut()
	{
		return shortcut;
	}
	
	public String name()
	{
		return getText();
	}
	
	public String description()
	{
		if (action != null)
			return action.description();
		
		return "Description not available in menu item";
	}

	public void invoke(Object input)
	{
		if (action == null)
			return;
		
		action.invoke(input);
	}

	@Override
	public void enable()
	{
		if (action == null)
			return;
		
		action.enable();
	}

	@Override
	public void disable()
	{
		if (action == null)
			return;
		
		action.disable();
	}

	@Override
	public boolean isEnabled()
	{
		return action == null ? false : action.isEnabled();
	}
	
}
