package xivvic.console.app;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

import xivvic.console.action.Action;
import xivvic.console.action.BatchAction;
import xivvic.console.interact.Stdin;
import xivvic.console.menu.MenuManager;

/**
 *
 * App -- base class for console applications.
 * Provides callback actions that will be called
 *
 * A) to initialize the application
 * B) to prepare and run batch commands
 * C) following every command execution
 * D) after the user has invoked a quit command
 *
 */

public class ConsoleApp
{
	/**
	 * Note these formatters are not thread safe !!
	 *
	 */
	private DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
	private DateFormat tf = DateFormat.getTimeInstance(DateFormat.LONG);
	private String  message;

	private BatchAction batchCallback;
	private Action initAction;
	private Action completeAction;
	private Action postCommand;
	private PrintStream out = System.out;
	private MenuManager mm;


	/**
	 * Register an action for the the application to perform before it begins
	 * execution.  Returns the previous action, if for some reason it is
	 * called more than once.
	 *
	 */
	public Action registerInitAction(Action action)
	{
		Action prev = initAction;
		initAction  = action;
		return prev;
	}

	/**
	 * Register an action for the the application to perform after the
	 * console application completes.
	 *
	 */
	public Action registerCompleteAction(Action action)
	{
		Action prev = completeAction;
		completeAction   = action;
		return prev;
	}

	/**
	 * Register an action for the the application to perform after the
	 * console application completes.
	 *
	 */
	public Action registerPostMenuAction(Action action)
	{
		Action prev = postCommand;
		postCommand      = action;
		return prev;
	}

	public void registerBatch(BatchAction ba)
	{
		batchCallback = ba;
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
		if (initAction == null)
		{
			setMessage("Starting console application.");
			out.println(message);
		}
		else
		{
			initAction.invoke(null);
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

