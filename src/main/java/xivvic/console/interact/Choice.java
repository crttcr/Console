package xivvic.console.interact;

import java.util.LinkedList;
import java.util.List;

/**
 * This is information represents a choice that a user can make.  It typically exists
 * within a Choices object.  It also contains a list of UserInput objects that will guide
 * the user to provide configuration information associated with this choice.
 *
 * @author reid
 *
 */
public class Choice
{
	private final String label;
	private final String description;
	private       List<UserInput> list;
	private       String[] bindings;

	public Choice(String label, String desc)
	{
		this.label = label;
		this.description = desc;
	}

	public String label()
	{
		return label;
	}

	public String description()
	{
		return description;
	}

	public void addInput(UserInput input)
	{
		if (input == null)
		{
			return;
		}

		if (list == null)
		{
			list = new LinkedList<>();
		}

		list.add(input);
	}

	public UserInput[] inputs()
	{
		if (list == null)
		{
			return new UserInput[0];
		}

		return list.toArray(new UserInput[0]);
	}

	public String[] bindings()
	{
		return bindings;
	}

	public void setBindings(String[] values)
	{
		if (values == null)
		{
			bindings = null;
		}
		else
		{
			bindings = values.clone();
		}
	}

	@Override
	public String toString()
	{
		return "Choice(" + label + ", " + inputCount() + ", " + bindingCount() + ")";
	}

	private int inputCount()
	{
		return list == null ? 0 : list.size();
	}

	private int bindingCount()
	{
		return bindings == null ? 0 : bindings.length;
	}

}
