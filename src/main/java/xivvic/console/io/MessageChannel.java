package xivvic.console.io;

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
}
