package hu.zforgo.junit.tools.configuration;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SimpleConfigurationTest extends AbstractConfigurationTest {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleConfigurationTest.class);

	private static final String PROPERTIES_NAME = "test.properties";
	private static final String MISSING_PROPERTIES_NAME = "missing.properties";

	private static final byte byteValue = 42;
	private static final byte byteValueNegative = -37;
	private static final byte byteValueDefaultValue = 89;

	private static final short shortValue = 32549;
	private static final short shortValueNegative = -12557;
	private static final short shortValueDefaultValue = 6899;

	private static final int intValue = 43801;
	private static final int intValueNegative = -697654;
	private static final int intValueDefaultValue = -876899;


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

	@Test
	public void booleanTests() throws IOException {
		LOG.info("Starting boolean checks...");

		//Checks for boolean
		assertThat(c.boolValue("basic.boolean")).isTrue();
		assertThat(c.boolValue("numeric.boolean")).isTrue();
		assertThat(c.boolValue("numeric.boolean.number")).isFalse();
		assertThat(c.boolValue("numeric.boolean.false")).isFalse();
		checkMissingKey(() -> c.boolValue("missing.boolean"));
		assertThat(c.boolValue("basic.boolean", false)).isTrue();
		assertThat(c.boolValue("missing.boolean", true)).isTrue();
		assertThat(c.boolValue("missing.boolean", false)).isFalse();
	}

	@Test
	public void byteTests() throws IOException {
		LOG.info("Starting byte checks...");

		//Checks for byte
		assertThat(c.byteValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.byteValue("byte.value.negative")).isEqualTo(byteValueNegative);
		checkMissingKey(() -> c.byteValue("missing.byte"));
		assertThat(c.byteValue("byte.value", byteValueDefaultValue)).isEqualTo(byteValue);
		assertThat(c.byteValue("byte.value.negative", byteValueDefaultValue)).isEqualTo(byteValueNegative);
		assertThat(c.byteValue("missing.byte", byteValueDefaultValue)).isEqualTo(byteValueDefaultValue);
		checkInvalidNumber(() -> c.byteValue("string.value"));
		checkInvalidNumber(() -> c.byteValue("string.value", byteValueDefaultValue));
		checkInvalidNumber(() -> c.byteValue("float.value.low"));
		checkInvalidNumberRange(() -> c.byteValue("int.value"));
	}

	@Test
	public void shortTests() throws IOException {
		LOG.info("Starting short checks...");

		//Checks for short
		assertThat(c.shortValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.shortValue("byte.value.negative")).isEqualTo(byteValueNegative);
		assertThat(c.shortValue("short.value")).isEqualTo(shortValue);
		assertThat(c.shortValue("short.value.negative")).isEqualTo(shortValueNegative);
		checkMissingKey(() -> c.shortValue("missing.short"));
		assertThat(c.shortValue("missing.short", shortValueDefaultValue)).isEqualTo(shortValueDefaultValue);
		checkInvalidNumber(() -> c.shortValue("string.value"));
		checkInvalidNumber(() -> c.shortValue("string.value", shortValueDefaultValue));
		checkInvalidNumber(() -> c.shortValue("float.value.low"));
		checkInvalidNumberRange(() -> c.shortValue("int.value"));
	}

	@Test
	public void intTests() throws IOException {
		LOG.info("Starting int checks...");

		//Checks for int
		assertThat(c.intValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.intValue("byte.value.negative")).isEqualTo(byteValueNegative);
		assertThat(c.intValue("short.value")).isEqualTo(shortValue);
		assertThat(c.intValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.intValue("short.value")).isEqualTo(shortValue);
		assertThat(c.intValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.intValue("int.value")).isEqualTo(intValue);
		assertThat(c.intValue("int.value.negative")).isEqualTo(intValueNegative);
		checkMissingKey(() -> c.intValue("missing.int"));
		assertThat(c.intValue("missing.int", intValueDefaultValue)).isEqualTo(intValueDefaultValue);
		checkInvalidNumber(() -> c.intValue("string.value"));
		checkInvalidNumber(() -> c.intValue("string.value", intValueDefaultValue));
		checkInvalidNumber(() -> c.intValue("float.value.low"));

		/*
		Must be checked by checkInvalidNumber because
		Integer.parseInt thorws NFEx with 'For input string:'
		message instead of 'Value out of range.'
		*/
		checkInvalidNumber(() -> c.intValue("long.value"));
	}

	public void longTest() throws IOException {
		LOG.info("Starting long checks...");

		assertThat(c.intValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.intValue("byte.value.negative")).isEqualTo(byteValueNegative);
		assertThat(c.intValue("short.value")).isEqualTo(shortValue);
		assertThat(c.intValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.intValue("short.value")).isEqualTo(shortValue);
		assertThat(c.intValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.intValue("int.value")).isEqualTo(intValue);
		assertThat(c.intValue("int.value.negative")).isEqualTo(intValueNegative);
//TODO finish
	}
}