package hu.zforgo.junit.tools.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HierarchicalPrimitiveTest extends HierarchicalConfigurationTest {

	private static final byte byteValue = 42;
	private static final byte byteValueNegative = -37;

	private static final short shortValue = 32549;
	private static final short shortValueNegative = -12557;

	private static final int intValue = 43801;
	private static final int intValueNegative = -697654;

	private static final long longValue = 322147483647L;
	private static final long longValueNegative = -322147483647L;

	private static final float floatValue = 11223372036854775807f;
	private static final float floatValueNegative = -11223372036854775807f;

	private static final double doubleValue = 54340282346638528860000000000000000000000.000000;
	private static final double doubleValueNegative = -54340282346638528860000000000000000000000.000000;

	public HierarchicalPrimitiveTest(String type, Configuration c) {
		super(type, c);
	}

	@Test
	public void booleanTests() {
		assertThat(c.boolValue("boolean.first")).isTrue();
		assertThat(c.boolValue("boolean.wrap")).isTrue();
		assertThat(c.boolValue("boolean.firstwrong")).isTrue();
		assertThat(c.boolValue("boolean.allset")).isTrue();
		checkMissingKey(() -> c.boolValue("missing.boolean"));
		assertThat(c.boolValue("missing.boolean", true)).isTrue();
		assertThat(c.boolValue("boolean.allset", false)).isTrue();
		assertThat(c.boolValue("boolean.wrap", false)).isTrue();
	}

	@Test
	public void byteTest() {
		assertThat(c.byteValue("byte.first")).isEqualTo(byteValue);
		assertThat(c.byteValue("byte.first.negative")).isEqualTo(byteValueNegative);
		assertThat(c.byteValue("byte.wrap")).isEqualTo(byteValue);
		assertThat(c.byteValue("byte.firstwrong")).isEqualTo(byteValue);
		assertThat(c.byteValue("byte.allset")).isEqualTo(byteValue);
		checkMissingKey(() -> c.byteValue("missing.byte"));
		assertThat(c.byteValue("missing.byte", byteValue)).isEqualTo(byteValue);
		assertThat(c.byteValue("byte.allset", byteValueNegative)).isEqualTo(byteValue);
		assertThat(c.byteValue("byte.wrap", byteValueNegative)).isEqualTo(byteValue);
	}

	@Test
	public void shortTest() {
		assertThat(c.shortValue("short.first")).isEqualTo(shortValue);
		assertThat(c.shortValue("short.first.negative")).isEqualTo(shortValueNegative);
		assertThat(c.shortValue("short.wrap")).isEqualTo(shortValue);
		assertThat(c.shortValue("short.firstwrong")).isEqualTo(shortValue);
		assertThat(c.shortValue("short.allset")).isEqualTo(shortValue);
		checkMissingKey(() -> c.shortValue("missing.short"));
		assertThat(c.shortValue("missing.short", shortValue)).isEqualTo(shortValue);
		assertThat(c.shortValue("short.allset", shortValueNegative)).isEqualTo(shortValue);
		assertThat(c.shortValue("short.wrap", shortValueNegative)).isEqualTo(shortValue);
	}

	@Test
	public void intTest() {
		assertThat(c.intValue("int.first")).isEqualTo(intValue);
		assertThat(c.intValue("int.first.negative")).isEqualTo(intValueNegative);
		assertThat(c.intValue("int.wrap")).isEqualTo(intValue);
		assertThat(c.intValue("int.firstwrong")).isEqualTo(intValue);
		assertThat(c.intValue("int.allset")).isEqualTo(intValue);
		checkMissingKey(() -> c.intValue("missing.int"));
		assertThat(c.intValue("missing.int", intValue)).isEqualTo(intValue);
		assertThat(c.intValue("int.allset", intValueNegative)).isEqualTo(intValue);
		assertThat(c.intValue("int.wrap", intValueNegative)).isEqualTo(intValue);
	}

	@Test
	public void longTest() {
		assertThat(c.longValue("long.first")).isEqualTo(longValue);
		assertThat(c.longValue("long.first.negative")).isEqualTo(longValueNegative);
		assertThat(c.longValue("long.wrap")).isEqualTo(longValue);
		assertThat(c.longValue("long.firstwrong")).isEqualTo(longValue);
		assertThat(c.longValue("long.allset")).isEqualTo(longValue);
		checkMissingKey(() -> c.longValue("missing.long"));
		assertThat(c.longValue("missing.long", longValue)).isEqualTo(longValue);
		assertThat(c.longValue("long.allset", longValueNegative)).isEqualTo(longValue);
		assertThat(c.longValue("long.wrap", longValueNegative)).isEqualTo(longValue);
	}

	@Test
	public void floatTest() {
		assertThat(c.floatValue("float.first")).isEqualTo(floatValue);
		assertThat(c.floatValue("float.first.negative")).isEqualTo(floatValueNegative);
		assertThat(c.floatValue("float.wrap")).isEqualTo(floatValue);
		assertThat(c.floatValue("float.firstwrong")).isEqualTo(floatValue);
		assertThat(c.floatValue("float.allset")).isEqualTo(floatValue);
		checkMissingKey(() -> c.floatValue("missing.float"));
		assertThat(c.floatValue("missing.float", floatValue)).isEqualTo(floatValue);
		assertThat(c.floatValue("float.allset", floatValueNegative)).isEqualTo(floatValue);
		assertThat(c.floatValue("float.wrap", floatValueNegative)).isEqualTo(floatValue);
	}

	@Test
	public void doubleTest() {
		assertThat(c.doubleValue("double.first")).isEqualTo(doubleValue);
		assertThat(c.doubleValue("double.first.negative")).isEqualTo(doubleValueNegative);
		assertThat(c.doubleValue("double.wrap")).isEqualTo(doubleValue);
		assertThat(c.doubleValue("double.firstwrong")).isEqualTo(doubleValue);
		assertThat(c.doubleValue("double.allset")).isEqualTo(doubleValue);
		checkMissingKey(() -> c.doubleValue("missing.double"));
		assertThat(c.doubleValue("missing.double", doubleValue)).isEqualTo(doubleValue);
		assertThat(c.doubleValue("double.allset", doubleValueNegative)).isEqualTo(doubleValue);
		assertThat(c.doubleValue("double.wrap", doubleValueNegative)).isEqualTo(doubleValue);
	}
}