package hu.zforgo.testing.tools.configuration;

import org.junit.Test;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class SimplePrimitiveTest extends SimpleConfigurationTest {

	public SimplePrimitiveTest(String type, Configuration c) {
		super(type, c);
	}

	@Test
	public void booleanTests() {
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
	public void byteTests() {
		assertThat(c.byteValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.byteValue("byte.value.negative")).isEqualTo(byteValueNegative);
		checkMissingKey(() -> c.byteValue("missing.byte"));
		assertThat(c.byteValue("byte.value", byteDefaultValue)).isEqualTo(byteValue);
		assertThat(c.byteValue("byte.value.negative", byteDefaultValue)).isEqualTo(byteValueNegative);
		assertThat(c.byteValue("missing.byte", byteDefaultValue)).isEqualTo(byteDefaultValue);
		checkInvalidNumber(() -> c.byteValue("string.value"));
		checkInvalidNumber(() -> c.byteValue("string.value", byteDefaultValue));
		checkInvalidNumber(() -> c.byteValue("float.value.low"));
		checkInvalidNumberRange(() -> c.byteValue("int.value"));
	}

	@Test
	public void shortTests() {
		assertThat(c.shortValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.shortValue("byte.value.negative")).isEqualTo(byteValueNegative);
		assertThat(c.shortValue("short.value")).isEqualTo(shortValue);
		assertThat(c.shortValue("short.value.negative")).isEqualTo(shortValueNegative);
		checkMissingKey(() -> c.shortValue("missing.short"));
		assertThat(c.shortValue("missing.short", shortDefaultValue)).isEqualTo(shortDefaultValue);
		checkInvalidNumber(() -> c.shortValue("string.value"));
		checkInvalidNumber(() -> c.shortValue("string.value", shortDefaultValue));
		checkInvalidNumber(() -> c.shortValue("float.value.low"));
		checkInvalidNumberRange(() -> c.shortValue("int.value"));
	}

	@Test
	public void intTests() {
		assertThat(c.intValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.intValue("byte.value.negative")).isEqualTo(byteValueNegative);
		assertThat(c.intValue("short.value")).isEqualTo(shortValue);
		assertThat(c.intValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.intValue("short.value")).isEqualTo(shortValue);
		assertThat(c.intValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.intValue("int.value")).isEqualTo(intValue);
		assertThat(c.intValue("int.value.negative")).isEqualTo(intValueNegative);
		checkMissingKey(() -> c.intValue("missing.int"));
		assertThat(c.intValue("missing.int", intDefaultValue)).isEqualTo(intDefaultValue);
		checkInvalidNumber(() -> c.intValue("string.value"));
		checkInvalidNumber(() -> c.intValue("string.value", intDefaultValue));
		checkInvalidNumber(() -> c.intValue("float.value.low"));

		/*
		Must be checked by checkInvalidNumber because
		Integer.parseInt thorws NFEx with 'For input string:'
		message instead of 'Value out of range.'
		*/
		checkInvalidNumber(() -> c.intValue("long.value"));
	}

	@Test
	public void longTest() {
		assertThat(c.longValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.longValue("byte.value.negative")).isEqualTo(byteValueNegative);
		assertThat(c.longValue("short.value")).isEqualTo(shortValue);
		assertThat(c.longValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.longValue("short.value")).isEqualTo(shortValue);
		assertThat(c.longValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.longValue("int.value")).isEqualTo(intValue);
		assertThat(c.longValue("int.value.negative")).isEqualTo(intValueNegative);

		checkMissingKey(() -> c.longValue("missing.long"));
		assertThat(c.longValue("missing.long", longDefaultValue)).isEqualTo(longDefaultValue);
		assertThat(c.longValue("long.max.value", Long.MIN_VALUE)).isEqualTo(Long.MAX_VALUE);
		assertThat(c.longValue("long.min.value", Long.MAX_VALUE)).isEqualTo(Long.MIN_VALUE);

		assertThat(c.longValue("long.min.value")).isEqualTo(Long.MIN_VALUE);
		assertThat(c.longValue("long.max.value")).isEqualTo(Long.MAX_VALUE);

		checkInvalidNumber(() -> c.longValue("string.value"));
		checkInvalidNumber(() -> c.longValue("string.value", intDefaultValue));
		checkInvalidNumber(() -> c.longValue("float.value.low"));
		checkInvalidNumber(() -> c.longValue("long.value.withmark"));
		checkInvalidNumber(() -> c.longValue("long.min.value.withspace"));
		checkInvalidNumber(() -> c.longValue("float.value"));
	}

	@Test
	public void floatTest() {
		assertThat(c.floatValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.floatValue("byte.value.negative")).isEqualTo(byteValueNegative);
		assertThat(c.floatValue("short.value")).isEqualTo(shortValue);
		assertThat(c.floatValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.floatValue("short.value")).isEqualTo(shortValue);
		assertThat(c.floatValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.floatValue("int.value")).isEqualTo(intValue);
		assertThat(c.floatValue("int.value.negative")).isEqualTo(intValueNegative);
		assertThat(c.floatValue("long.min.value")).isEqualTo(Long.MIN_VALUE);
		assertThat(c.floatValue("long.max.value")).isEqualTo(Long.MAX_VALUE);

		checkMissingKey(() -> c.floatValue("missing.float"));
		assertThat(c.floatValue("missing.float", floatValueLow)).isEqualTo(floatValueLow);
		assertThat(c.floatValue("float.value")).isEqualTo(floatValue);
		assertThat(c.floatValue("float.value.hasdecimals")).isEqualTo(floatValueHasdecimals);
		assertThat(c.floatValue("float.max.value")).isEqualTo(Float.MAX_VALUE);
//TODO negative float check

		assertThat(Float.isInfinite(c.floatValue("double.value"))).isTrue();


		checkInvalidNumber(() -> c.floatValue("string.value"));
		checkInvalidNumber(() -> c.floatValue("string.value", floatValue));
	}


	@Test
	public void doubleTest() {
		assertThat(c.doubleValue("byte.value")).isEqualTo(byteValue);
		assertThat(c.doubleValue("byte.value.negative")).isEqualTo(byteValueNegative);
		assertThat(c.doubleValue("short.value")).isEqualTo(shortValue);
		assertThat(c.doubleValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.doubleValue("short.value")).isEqualTo(shortValue);
		assertThat(c.doubleValue("short.value.negative")).isEqualTo(shortValueNegative);
		assertThat(c.doubleValue("int.value")).isEqualTo(intValue);
		assertThat(c.doubleValue("int.value.negative")).isEqualTo(intValueNegative);
		assertThat(c.doubleValue("long.min.value")).isEqualTo(Long.MIN_VALUE);
		assertThat(c.doubleValue("long.max.value")).isEqualTo(Long.MAX_VALUE);
		assertThat(c.doubleValue("float.value")).isEqualTo(floatValueAsDouble);

		checkMissingKey(() -> c.doubleValue("missing.double"));
		assertThat(c.doubleValue("missing.double", floatValueAsDouble)).isEqualTo(floatValueAsDouble);
		assertThat(c.doubleValue("double.value")).isEqualTo(doubleValue);
//TODO negative double checks
//		assertThat(c.doubleValue("double.value.negative")).isEqualTo(doubleValueNegative);


		checkInvalidNumber(() -> c.doubleValue("string.value"));
		checkInvalidNumber(() -> c.doubleValue("string.value", doubleValue));
	}
}
