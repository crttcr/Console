package xivvic.console;

public interface IAction
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
}
