package hu.zforgo.junit.tools.configuration;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.assertj.core.api.StrictAssertions.catchThrowable;

public abstract class AbstractConfigurationTest {

	protected static volatile Configuration c;

	@Before
	public void initConfiguration() throws IOException {
		if (c == null) {
			Configuration tmp = loadConfiguration();
			if (c == null) {
				c = tmp;
			}
		}
	}

	protected abstract Configuration loadConfiguration() throws IOException;

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
