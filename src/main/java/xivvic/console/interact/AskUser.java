package xivvic.console.interact;

/**
 * Defines an interface for getting information from a user.
 * Core code can use this interface without having to know any
 * details about how the application interacts with the user.
 * 
 */
public interface AskUser
{
	/**
	 * Get an int from the user.  Assume that it is required,
	 * given that it is not easy to send back both an int and a success flag.
	 */
	public int getInt(UserInput input);

	/**
	 * Get a String from the user.  Keep trying until the user's input passes
	 * any predicated associated with the UserInput object.
	 */
	public String getString(UserInput input);
	
	/**
	 * Provide the user with a selection of Choice objects and have them choose
	 * one.
	 */
	public Choice getChoice(Choices choices);
}
