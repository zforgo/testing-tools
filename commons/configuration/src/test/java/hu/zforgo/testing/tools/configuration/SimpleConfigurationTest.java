package hu.zforgo.testing.tools.configuration;

import org.junit.Test;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public abstract class SimpleConfigurationTest extends AbstractConfigurationTest {

	private static final String PROPERTIES_NAME = "test.properties";

	public SimpleConfigurationTest(String type, Configuration c) {
		super(type, c);
	}

	private static Path path() throws URISyntaxException {
		URL root = Thread.currentThread().getContextClassLoader().getResource("");
		assert root != null : "Couldn't find root url";
		return Paths.get(root.toURI()).resolve("configuration/levels/first");
	}

	@Parameterized.Parameters
	public static Collection<Object[]> loadConfiguration() throws IOException, URISyntaxException {
		return Arrays.asList(new Object[][]{
				{"builtIn_classLoader", ConfigurationFactory.load("configuration/levels/first/" + PROPERTIES_NAME)},
				{"path", ConfigurationFactory.load("test.properties", path())},
				{"custom_classLoader", ConfigurationFactory.load("configuration/levels/first/" + PROPERTIES_NAME, SimpleConfigurationTest.class.getClassLoader())}
		});
	}

	@Test
	public void tryLoadMissing() throws URISyntaxException {
		{
			Throwable t = catchThrowable(() -> ConfigurationFactory.load(MISSING_PROPERTIES_NAME));
			assertThat(t).isInstanceOf(IOException.class)
					.hasMessage(String.format("Couldn't find properties file [%s] on classpath", MISSING_PROPERTIES_NAME));
		}
		{
			Throwable t = catchThrowable(() -> ConfigurationFactory.load(MISSING_PROPERTIES_NAME, path()));
			assertThat(t).isInstanceOf(NoSuchFileException.class);
		}
	}
}