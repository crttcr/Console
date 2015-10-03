package console.menu;

import xivvic.console.action.ActionBase;

public class BatchAction
	extends ActionBase
{
	private static String NAME        = "Batch Action";
	private static String DESCRIPTION = "Action that will hold commands for batch execution";
	String[] cmds = null;
	
	
	public BatchAction()
	{
		super(NAME, DESCRIPTION);
	}
	
	public BatchAction(String[] commands)
	{
		super(NAME, DESCRIPTION, true);
		cmds       = commands;
	}
	
	public String[] commands()
	{
		return cmds;
	}

	@Override
	public void internal_invoke(Object param)
	{
		// At present ...
		// Nothing to do in this method.  Each of the commands in the array
		// will be passed to the MenuManager to invoke.
		//
		
		System.out.println("Batch command: [" + param.toString() + "]");
	}
}
