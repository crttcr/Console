package xivvic.console.action;

public interface Action
{
	/**
	 * The name of the action.  This should be unique among actions.
	 * 
	 * @return
	 */
	public String name();
	
	/**
	 * A description of the action
	 * @return
	 */
	public String description();
	
	/**
	 * The action method.  Call this method to perform the action.
	 * 
	 * @param param
	 */
	public void invoke(Object param);
	
	/**
	 * Enables the action so that it can be invoked
	 * 
	 */
	public void enable();
	
	/**
	 * Set the action's state to disabled.  Disabled actions
	 * should not be able to be invoked.
	 * 
	 */
	public void disable();
	
	/**
	 * Returns the enablement state of the action
	 * 
	 */
	public boolean isEnabled();
	
	
}
