package xivvic.console.interact;

import java.util.function.Predicate;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * Class to be used to gather information from the user. A prompt is required, but other fields are optional.
 *
 * @author Reid
 */

@Data
@Builder
@Accessors(fluent = true)
public class UserInput
{
	@Builder.Default
	final 	private String prompt = "Please provide input";

	final 	private String title;
	final 	private String defaultValue;
	final 	private String help;
	final 	private Boolean optional;

	final 	Predicate<String> inputPredicate;
}
