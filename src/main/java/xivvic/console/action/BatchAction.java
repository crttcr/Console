package xivvic.console.action;

import java.util.Objects;

/**
 * A batch Action is different than an initialization action. The initialization action is a normal
 * action that just happens to get run during startup.
 *
 * The batch action runs after initialization, before the normal menu loop. It contains a series of
 * text commands that get translated into other actions as if the user had typed in the commands
 * immediately after startup.
 *
 * @author reid
 */
public class BatchAction
extends ActionBase
{
	private static String NAME        = "Batch Action";
	private static String DESCRIPTION = "Action that will hold commands for batch execution";
	String[] cmds = null;


	public BatchAction(String[] commands)
	{
		super(NAME, DESCRIPTION, true);
		cmds = Objects.requireNonNull(commands).clone();
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
