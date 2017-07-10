package xivvic.console.action;
/**
 * This action serves as a convenience placeholder for an action
 * that has yet to be implemented or is meant to have no effect.
 *
 * All this class does is provide a NOP implementation for ActionBase's
 * abstract invoke_internal() method.
 *
 */
public class DummyAction
extends ActionBase
{
	/**
	 * Constructs a dummy action that is enabled or not based on the is_enabled parameter
	 *
	 * @param name a name for this placeholder action
	 * @param description a description for this placeholder action
	 */
	public DummyAction(String name, String description, boolean is_enabled)
	{
		super(name, description, is_enabled);
	}

	/**
	 * Constructs a dummy action that is disabled by default
	 *
	 * @param name a name for this placeholder action
	 * @param description a description for this placeholder action
	 */
	public DummyAction(String name, String description)
	{
		super(name, description, false);
	}

	@Override
	protected void internal_invoke(Object param)
	{
		// Does nothing by design
		//
	}

}
