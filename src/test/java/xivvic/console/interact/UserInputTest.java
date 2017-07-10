package xivvic.console.interact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import xivvic.console.interact.UserInput;
import xivvic.console.interact.UserInput.UserInputBuilder;

public class UserInputTest
{

	@Test
	public void onBuilder_thenReturnBuilder()
	{
		assertNotNull(UserInput.builder());
	}

	@Test
	public void onBuild_withNoPrompt_thenUseDeault()
	{
		UserInputBuilder builder = UserInput.builder();
		UserInput          input = builder.build();
		assertNotNull(input.prompt());
	}

	@Test
	public void onBuild_withPrompt_thenUsePrompt()
	{
		// Arrange
		//
		String prompt = "Do you care?";
		UserInputBuilder builder = UserInput.builder();
		builder.prompt(prompt);

		// Act
		//
		UserInput          input = builder.build();

		// Assert
		//
		assertEquals(prompt, input.prompt());
	}
}
