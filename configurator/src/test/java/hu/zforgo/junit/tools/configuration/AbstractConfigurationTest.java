package hu.zforgo.junit.tools.configuration;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.NoSuchElementException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;


@RunWith(Parameterized.class)
public abstract class AbstractConfigurationTest {

	@Rule
	public LoggerRule rule;

	public Configuration c;


	public AbstractConfigurationTest(String type, Configuration c) {
		this.c = c;
		rule = new LoggerRule(type);
	}

	protected void checkMissingKey(final ThrowableAssert.ThrowingCallable e) {
		Throwable t = catchThrowable(e);
		assertThat(t).isInstanceOf(NoSuchElementException.class)
				.hasMessageStartingWith("Configuration key not found: ");
	}

	protected void checkInvalidNumber(final ThrowableAssert.ThrowingCallable e) {
		numericCheck(e, "For input string:");
	}

	protected void checkInvalidNumberRange(final ThrowableAssert.ThrowingCallable e) {
		numericCheck(e, "Value out of range.");
	}

	private void numericCheck(final ThrowableAssert.ThrowingCallable e, final String messagePrefix) {
		Objects.requireNonNull(messagePrefix, "messagePrefix is missing");

		Throwable t = catchThrowable(e);
		assertThat(t).isInstanceOf(NumberFormatException.class)
				.hasMessageStartingWith(messagePrefix);
	}
}
