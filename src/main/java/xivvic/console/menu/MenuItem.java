package xivvic.console.menu;

import lombok.Data;
import lombok.experimental.Accessors;
import xivvic.console.action.Action;
import xivvic.console.util.Enablement;

@Data
@Accessors(fluent = true)
public class MenuItem
implements Enablement
{
	private final String     text;
	private final String shortcut;
	private final Action   action;

	public MenuItem(String text, String shortcut, Action action)
	{
		if (text == null || text.length() == 0)
		{
			throw new IllegalArgumentException("MenuItem constructor requires valid text");
		}

		this.text     = text;
		this.action   = action;
		this.shortcut = shortcut;
	}

	public String name()
	{
		return text();
	}

	public String description()
	{
		if (action != null)
		{
			return action.description();
		}

		return "Action not established for this menu item.";
	}

	public void invoke(Object input)
	{
		if (action == null)
		{
			return;
		}

		action.invoke(input);
	}

	@Override
	public void enable()
	{
		if (action == null)
		{
			return;
		}

		action.enable();
	}

	@Override
	public void disable()
	{
		if (action == null)
		{
			return;
		}

		action.disable();
	}

	@Override
	public boolean is_enabled()
	{
		return action == null ? false : action.is_enabled();
	}

}
