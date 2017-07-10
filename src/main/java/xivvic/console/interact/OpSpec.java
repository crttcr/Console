package xivvic.console.interact;

import lombok.Data;
import lombok.experimental.Accessors;
import xivvic.console.action.Action;

/**
 * OpSpec is a specification for an operation to be performed.
 *
 * When a user inputs a command or operation to a console application, it is frequently
 * structured as the operation with optional parameters.
 *
 * This class provides a container for the results of processing a users's inputs.
 * It is used to capture both the text that indicates an action and any following parameters.
 *
 * Additionally, it can be uses to store a reference to an Action that will implement
 * the functionality.
 */


@Data
@Accessors(fluent = true)
public class OpSpec
{
	private final String  text;
	private final String  parameter;
	private Action    action;

	public OpSpec(String text, String param)
	{
		this.text = text;
		this.parameter = param;
	}
}
