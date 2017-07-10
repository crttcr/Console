package xivvic.console.interact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static xivvic.console.interact.MessageChannel.ERROR;
import static xivvic.console.interact.MessageChannel.HELP;
import static xivvic.console.interact.MessageChannel.INVALID_INPUT;
import static xivvic.console.interact.MessageChannel.STATUS;

import java.util.Set;

import org.junit.Test;

import xivvic.console.interact.MessageChannel;

public class MessageChannelTest
{
	@Test
	public void onEmptySet_thenReturnEmptySet()
	{
		Set<MessageChannel> result = MessageChannel.emptySet();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void onModifyAttempt_withEmptySet_thenThrowException()
	{
		Set<MessageChannel> result = MessageChannel.emptySet();
		result.add(ERROR);
	}

	@Test
	public void onSingleton_thenReturnSetContainingMessageChannel()
	{
		Set<MessageChannel> result = MessageChannel.singleton(INVALID_INPUT);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertTrue(result.contains(INVALID_INPUT));
	}

	@Test
	public void onSingleton_withNull_thenReturnEmptySet()
	{
		Set<MessageChannel> result = MessageChannel.singleton(null);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void onModifyAttempt_withSingletonSet_thenThrowException()
	{
		Set<MessageChannel> result = MessageChannel.singleton(STATUS);
		result.add(ERROR);
	}

	@Test
	public void onChannelSet_withNullObject_thenReturnEmptySet()
	{
		MessageChannel          nc = null;
		Set<MessageChannel> result = MessageChannel.channelSet(nc);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void onChannelSet_withNullArray_thenReturnEmptySet()
	{
		MessageChannel[]     array = null;
		Set<MessageChannel> result = MessageChannel.channelSet(array);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void onChannelSet_withZeroLengthArray_thenReturnEmptySet()
	{
		MessageChannel[]     array = new MessageChannel[0];
		Set<MessageChannel> result = MessageChannel.channelSet(array);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void onChannelSet_withMultipleChannels_thenReturnCorrectSet()
	{
		// Act
		Set<MessageChannel> result = MessageChannel.channelSet(STATUS, ERROR, HELP);

		// Assert
		//
		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.contains(STATUS));
		assertTrue(result.contains(ERROR));
		assertTrue(result.contains(HELP));
		assertFalse(result.contains(INVALID_INPUT));
	}

}
