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

	protected static final byte byteValue = 42;
	protected static final byte byteValueNegative = -37;
	protected static final byte byteDefaultValue = 89;

	protected static final short shortValue = 32549;
	protected static final short shortValueNegative = -12557;
	protected static final short shortDefaultValue = 6899;

	protected static final int intValue = 43801;
	protected static final int intValueNegative = -697654;
	protected static final int intDefaultValue = -876899;

	protected static final long longDefaultValue = -876899L;

	protected static final float floatValueLow = 12.8767f;
	protected static final float floatValue = 11223372036854775807f;
	protected static final double floatValueAsDouble = 11223372036854775807d;
	protected static final float floatValueHasdecimals = 11223372036854775807.234954657821f;

	protected static final double doubleValue = 54340282346638528860000000000000000000000.000000;
	protected static final double doubleValueNegative = -5340282346638528860000000000000000000000.000000;
	protected static final String PROPERTIES_NAME = "test.properties";

	private static final String[] stringArrayValue = {"foo", "bar", "baz", "bad", "robot"};

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


	@Test
	public void submapTest() {
		Configuration sub = c.submap("string.array.");
		assertThat(sub.asMap().size()).isEqualTo(7);
		assertThat(sub.getString("must_not_be", null)).isNull();
		assertThat(sub.getString("string.value", null)).isNull();
		assertThat(sub.getStringArray("colon", ':')).isEqualTo(stringArrayValue);
		checkMissingKey(() -> sub.getString("string.value"));
	}

	@Test
	public void remainsTest() {
		assertThat(c.remains("string.").asMap().size()).isEqualTo(23);
		assertThat(c.remains("string.array").asMap().size()).isEqualTo(29);
		assertThat(c.remains("string.array.").asMap().size()).isEqualTo(30);
		assertThat(c.remains("there_isnt_any").asMap().size()).isEqualTo(37);
	}
}