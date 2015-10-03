package xivvic.console.action;
/**
 * This action serves as a convenience placeholder for an action
 * that has yet to be implemented or is meant to have no effect.
 * 
 * All this class does is provide a NOP implementation for ActionBase's
 * abstract invoke_internal() method.
 * 
 * @author Reid
 *
 */
public class DummyAction
	extends ActionBase
{	
	/**
	 * Constructs a dummy action that is enabled
	 * @param name a name for this placeholder action
	 * @param description a description for this placeholder action
	 */
	public DummyAction(String name, String description, boolean is_enabled)
	{
		super(name, description, is_enabled);
	}

	/**
	 * Constructs a dummy action that is enabled
	 * @param name a name for this placeholder action
	 * @param description a description for this placeholder action
	 */
	public DummyAction(String name, String description)
	{
		super(name, description);
	}

	@Override
	protected void internal_invoke(Object param)
	{
		// Does nothing by design
		//
	}

}
