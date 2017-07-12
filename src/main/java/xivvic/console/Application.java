package xivvic.console;

import static xivvic.console.action.ActionTiming.INITIALIZE;
import static xivvic.console.action.ActionTiming.POST_INITIALIZE;
import static xivvic.console.action.ActionTiming.RUNTIME_POST_ACTION_EXEC;
import static xivvic.console.action.ActionTiming.SHUTDOWN;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import xivvic.console.action.Action;
import xivvic.console.action.ActionManager;
import xivvic.console.action.ActionTiming;
import xivvic.console.action.BatchAction;
import xivvic.console.interact.Stdin;
import xivvic.console.menu.MenuManager;

/**
 * Application -- base class for console applications.
 * Provides callback actions that will be called
 *
 * A) to initialize the application
 * B) to prepare and run batch commands
 * C) following every command execution
 * D) after the user has invoked a quit command
 *
 */

public class Application
{
	/**
	 * Note these formatters are not thread safe !!
	 *
	 */
	private DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
	private DateFormat tf = DateFormat.getTimeInstance(DateFormat.LONG);
	private String  message;

	private BatchAction batchCallback = null;

	private Action completeAction;
	private PrintStream out = System.out;
	private MenuManager mm;
	private ActionManager am;

	public Application(MenuManager mm, ActionManager am)
	{
		this.mm = Objects.requireNonNull(mm);
		this.am = Objects.requireNonNull(am);
	}

	/**
	 * Register an action for the the application to perform before it begins
	 * execution.
	 *
	 */
	public void registerInitAction(Action action)
	{
		am.register(action, INITIALIZE);
	}

	public void registerBatch(BatchAction ba)
	{
		am.register(ba, null, ba.name());
	}

	/**
	 * Register an action for the the application to perform after
	 * each menu action
	 */
	public void registerPostMenuAction(Action action)
	{
		am.register(action, RUNTIME_POST_ACTION_EXEC);
	}

	/**
	 * Register an action for the the application to perform after the
	 * console application completes.
	 *
	 */
	public void registerCompleteAction(Action action)
	{
		am.register(action, SHUTDOWN);
	}

	/** Sets the output stream that will receive the console's text output.
	 * If NULL is passed in as a parameter, will revert to System.out
	 *
	 * @param output
	 * @return returns the previous output stream.
	 */
	public PrintStream setPrintStream(PrintStream output)
	{
		PrintStream prev = out;
		out = output == null ? System.out : output;
		return prev;
	}


	protected void setUp()
	{
		ActionTiming[] at = { INITIALIZE, POST_INITIALIZE };

		setMessage("Starting console application.");
		out.println(message);
		for (ActionTiming timing : at)
		{
			List<Action> actions = am.getActionsFor(timing);
			for (Action a : actions)
			{
				a.invoke(null);
			}
		}
	}

	private void complete()
	{
		if (completeAction == null)
		{
			setMessage("Done.");
			out.println(message);
		}
		else
		{
			completeAction.invoke(null);
		}
	}

	public void run()
	{
		String    input = null;

		while (true)
		{
			mm.display(out);

			do
			{
				if (input != null)
				{
					String msg = "Input [" + input + "] not recognized";
					out.println(msg);
				}

				input = Stdin.getString();
			}
			while (! mm.isValidSelection(input));

			mm.invoke(input);
			input = null;

			if (mm.isDone())
			{
				break;
			}
		}
	}

	public void doLifecycle()
	{
		setUp();
		batch();
		run();
		complete();
	}

	private void setMessage(String m)
	{
		String ts = timestamp();
		message   = ts + m;
	}

	private void message()
	{
		out.println(message);
	}

	private void batch()
	{
		if (batchCallback == null)
		{
			String message = "Batch callback not registered";
			setMessage(message);
			message();
			return;
		}

		String[] commands = batchCallback.commands();
		if (commands == null || commands.length == 0)
		{
			String message = "No batch input";
			out.println(message);
			return;
		}

		String message = "Executing [" + commands.length + "] batch commands.";
		setMessage(message);
		message();

		for (String cmd : commands)
		{
			batchCallback.invoke(cmd);  // Callback gets to see the command.
			mm.invoke(cmd);             // MenuManager executes application logic.
		}
	}

	private String timestamp()
	{
		Date        now = new Date();
		StringBuffer sb = new StringBuffer();

		sb.append(df.format(now));
		sb.append(" ");
		sb.append(tf.format(now));
		sb.append("\n");

		return sb.toString();
	}
}

