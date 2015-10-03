/* FILE:      ApplicationPrompt.java
 * CREATED:   Jun 4, 2006
 * CREATOR:   reid
 *
 * Copyright (c) 2005 Emroo.com
 * 
 */
package xivvic.console;

import java.io.PrintStream;


/**
 * ApplicationPrompt abstracts the prompt to a user.
 *
 */
public class ApplicationPrompt
{
	private static final String EMPTY_PROMPT = "%: ";
	private              String text = EMPTY_PROMPT;
	
	public void display(PrintStream out)
	{
		out.println(text);
	}
	
	public void reset()
	{
		text = EMPTY_PROMPT;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
