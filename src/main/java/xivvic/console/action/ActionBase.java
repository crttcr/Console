package xivvic.console.action;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ActionBase is a simple class that implements the Action interface.
 * Made to be subclassed.
 *
 * @author cturner
 *
 */
@Data
@Accessors(fluent = true)
public abstract class ActionBase
implements Action
{
	private final String name;
	private final String description;
	private boolean is_enabled;

	public ActionBase(String name, String description, boolean enabled)
	{
		this.name        = name;
		this.description = description;
		this.is_enabled  = enabled;
	}

	@Override
	public final String name()
	{
		if (name != null)
		{
			return name;
		}

		return this.getClass().getSimpleName();
	}

	// Template method to eliminate the need for Action subclasses to
	// check for enabled status
	//
	@Override
	public final void invoke(Object param)
	{
		if (! is_enabled)
		{
			return;
		}

		internal_invoke(param);
		// NOP.
	}

	/**
	 * This is the method that subclasses need to implement.  It is
	 * called by the public Action interface method {@link invoke(Object param)}
	 * after checking whether or not the action is enabled.
	 *
	 * @param param the action parameter passed to the invoke method
	 */
	protected abstract void internal_invoke(Object param);

	@Override
	public final void enable()
	{
		is_enabled = true;
	}

	@Override
	public final void disable()
	{
		is_enabled = false;
	}

}
