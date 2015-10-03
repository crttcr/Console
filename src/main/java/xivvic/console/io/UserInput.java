package xivvic.console.io;

import java.util.function.Predicate;

import xivvic.console.util.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Class to be used to gather information from the user. A prompt is required, but other fields are optional.
 * 
 * @author Reid
 */

@AutoValue
public abstract class UserInput
{
	public abstract String prompt();

	@Nullable
	public abstract String title();

	@Nullable
	public abstract String defaultValue();

	@Nullable
	public abstract String help();

	@Nullable
	public abstract Boolean optional();

	@Nullable
	public abstract Predicate<String> inputPredicate();

	@AutoValue.Builder
	abstract static class Builder
	{
		abstract Builder prompt(String text);

		abstract Builder title(String title);
		abstract Builder defaultValue(String defaultValue);
		abstract Builder help(String help);
		abstract Builder optional(Boolean optional);

		abstract Builder inputPredicate(Predicate<String> test);
		abstract UserInput build();
	}

}
