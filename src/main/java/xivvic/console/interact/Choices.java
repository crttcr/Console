package xivvic.console.interact;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a set of choices that the user can select among.
 * 
 * @author reid
 *
 */
public class Choices
{
	private final String prompt;
	private final Choice[] choices;
	
	public Choices(String prompt, Choice[] choices)
	{
		this.prompt = prompt;
		this.choices = choices;
	}

	public Choices(String prompt, String[] items)
	{
		this.prompt = prompt;
		
		if (items == null)
			throw new IllegalArgumentException("Null choices");
		
		List<Choice> list = new LinkedList<Choice>();
		for (String s : items)
		{
			Choice cx = new Choice(s, s);
			list.add(cx);
		}
		
		this.choices = list.toArray(new Choice[list.size()]);
	}
	
	/**
	 * Selection will be within the range 1 .. n.
	 * Need to convert it back to 0-based index.
	 * 
	 * @param chosen
	 * @return
	 */
	public Choice attemptSelection(int chosen)
	{
		if (choices == null || choices.length == 0)
			return null;
		
		chosen--;  // Convert back to 0 based index.
		
		if (chosen < 0 || chosen > choices.length)
			return null;
		
		return choices[chosen];
	}

	public Choice[] getChoices()
	{
		return choices;
	}

	public String getPrompt()
	{
		return prompt;
	}

	public int count()
	{
		if (choices == null || choices.length < 1)
			return 0;
		
		return choices.length;
	}
}
