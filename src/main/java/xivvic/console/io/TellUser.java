package xivvic.console.io;

/**
 * Defines an interface for sending application messages to the user.
 * Application code can use this interface without having to know any
 * details about how the messages are actually delivered.
 * 
 * For example, rather than have every action wired up with specific knowledge 
 * of how this application handles status and error messages, the actions simply
 * publish to this interface.  The application specific class that defines an 
 * implementation will know whatever mechanisms are appropriate.  
 * 
 * This way, there's never code like the line below distributed throughout the 
 * application.
 *  
 * Application.getUI().getStatusPane().getStatusText().setText("blah blah");
 * 
 * TellUser.userMessage(Channel.STATUS, "blah blah");
 * 
 */
public interface TellUser
{
	/**
	 * Table of data to be displayed to user.
	 */
	public void displayTable(String id, String[] content);
	/**
	 * Method exposed to let application components send a message to the user.
	 * 
	 */
	public void userMessage(MessageChannel channel, String topic, String msg);

}
