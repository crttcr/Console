package xivvic.console.util;

public interface Enablement
{
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
	public boolean is_enabled();

}
