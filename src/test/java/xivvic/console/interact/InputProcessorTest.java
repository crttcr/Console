package xivvic.console.interact;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import xivvic.console.action.ActionManager;
import xivvic.console.interact.InputProcessor;

@RunWith(MockitoJUnitRunner.class)
public class InputProcessorTest
{
	@Mock ActionManager am;
	@Test
	public void onProcessInput_withNullText_thenReturnEmptySpecification()
	{
		assertEquals(0, InputProcessor.processInput("ctx", null, am).length);
	}

	@Test
	public void onProcessInput_withEmptyText_thenReturnEmptySpecification()
	{
		assertEquals(0, InputProcessor.processInput("ctx", "", am).length);
	}

}
