package xivvic.console.input;

import java.util.Map;

public interface InputProcessor
{
	
	Map<String, Object> process(String params);

	/**
	 * Convenience method that simply casts to String and passes to {@link process(String)}
	 * @param params the object to process
	 * @return a map of arguments to their values
	 */
	Map<String, Object> process(Object params);

//	/**
//	 * Adds an action to perform once all the inputs have been processed
//	 * When this method is called multiple times, the runnable actions will
//	 * be processed in the order provided to the processor.
//	 * 
//	 * @param binder the runnable to execute.
//	 */
//	void addPostAction(Runnable binder);
}
