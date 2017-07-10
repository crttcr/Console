package xivvic.console.interact;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Enumerated type covering the types of messages that might be sent to a user.
 *
 * A Channel may represent a destinations for messages code locations can use to indicate
 * what type of message is being generated.  This is a simple approach
 * to having message channels for the application.  The channel concept
 * lets arbitrary locations in the application publish messages without
 * being tightly coupled to how the messages are handled and presented
 * to the user (or logged or whatever).
 *
 */
public enum MessageChannel
{
	STATUS,
	HELP,
	ERROR,
	INVALID_INPUT,
	;

	public static Set<MessageChannel> emptySet()
	{
		Set<MessageChannel> empty = EnumSet.noneOf(MessageChannel.class);
		return Collections.unmodifiableSet(empty);
	}

	public static Set<MessageChannel> singleton(MessageChannel mc)
	{
		if (mc == null)
		{
			return emptySet();
		}

		Set<MessageChannel> single = EnumSet.of(mc);
		return Collections.unmodifiableSet(single);
	}

	public static Set<MessageChannel> channelSet(MessageChannel ...channels)
	{
		if (channels        == null) return emptySet();
		if (channels.length ==    0) return emptySet();

		Set<MessageChannel> rv = EnumSet.noneOf(MessageChannel.class);

		for (MessageChannel c: channels)
		{
			if (c != null)
			{
				rv.add(c);
			}
		}

		if (rv.isEmpty())
		{
			return emptySet();
		}

		return Collections.unmodifiableSet(rv);
	}


}
