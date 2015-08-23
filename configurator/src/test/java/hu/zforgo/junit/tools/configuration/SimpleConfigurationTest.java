package hu.zforgo.junit.tools.configuration;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public abstract class SimpleConfigurationTest extends AbstractConfigurationTest {

	private static final String PROPERTIES_NAME = "test.properties";
	private static final String MISSING_PROPERTIES_NAME = "missing.properties";

	@Override
	protected Configuration loadConfiguration() throws IOException {
		if (c == null) {
			return ConfigurationFactory.load("simple/levels/first/" + PROPERTIES_NAME);
		}
		throw new IllegalStateException("Configuration must be loaded only once.");
	}

	@Test
	public void tryLoadMissing() throws IOException {
		Throwable t = catchThrowable(() -> ConfigurationFactory.load(MISSING_PROPERTIES_NAME));
		assertThat(t).isInstanceOf(IOException.class)
				.hasMessage(String.format("Couldn't find properties file [%s] on classpath", MISSING_PROPERTIES_NAME));
	}


}