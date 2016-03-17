package hu.zforgo.testing.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HierarchicalObjectTest extends HierarchicalConfigurationTest {

	private static final Byte byteValue = 42;
	private static final Byte byteValueNegative = -37;

	private static final Short shortValue = 32549;
	private static final Short shortValueNegative = -12557;

	private static final Integer intValue = 43801;
	private static final Integer intValueNegative = -697654;

	private static final Long longValue = 322147483647L;
	private static final Long longValueNegative = -322147483647L;

	private static final Float floatValue = 11223372036854775807f;
	private static final Float floatValueNegative = -11223372036854775807f;

	private static final Double doubleValue = 54340282346638528860000000000000000000000.000000;
	private static final Double doubleValueNegative = -54340282346638528860000000000000000000000.000000;

	private static final String stringValue = "Lorem ipsum color dem";
	private static final String stringValueAnother = "Another String";
	private static final String[] stringArrayValue = {"foo", "bar", "baz", "bad", "robot"};
	private static final String[] stringArrayValueAnother = {"another", "string"};

	public HierarchicalObjectTest(String type, Configuration c) {
		super(type, c);
	}

	@Test
	public void booleanTests() {
		assertThat(c.getBoolean("boolean.first")).isExactlyInstanceOf(Boolean.class).isTrue();
		assertThat(c.getBoolean("boolean.wrap")).isExactlyInstanceOf(Boolean.class).isTrue();
		assertThat(c.getBoolean("boolean.firstwrong")).isExactlyInstanceOf(Boolean.class).isTrue();
		assertThat(c.getBoolean("boolean.allset")).isExactlyInstanceOf(Boolean.class).isTrue();
		checkMissingKey(() -> c.getBoolean("missing.boolean"));
		assertThat(c.getBoolean("missing.boolean", true)).isExactlyInstanceOf(Boolean.class).isTrue();
		assertThat(c.getBoolean("boolean.allset", false)).isExactlyInstanceOf(Boolean.class).isTrue();
		assertThat(c.getBoolean("boolean.wrap", false)).isExactlyInstanceOf(Boolean.class).isTrue();
	}

	@Test
	public void byteTest() {
		assertThat(c.getByte("byte.first")).isExactlyInstanceOf(Byte.class).isEqualTo(byteValue);
		assertThat(c.getByte("byte.first.negative")).isExactlyInstanceOf(Byte.class).isEqualTo(byteValueNegative);
		assertThat(c.getByte("byte.wrap")).isExactlyInstanceOf(Byte.class).isEqualTo(byteValue);
		assertThat(c.getByte("byte.firstwrong")).isExactlyInstanceOf(Byte.class).isEqualTo(byteValue);
		assertThat(c.getByte("byte.allset")).isExactlyInstanceOf(Byte.class).isEqualTo(byteValue);
		checkMissingKey(() -> c.getByte("missing.byte"));
		assertThat(c.getByte("missing.byte", byteValue)).isExactlyInstanceOf(Byte.class).isEqualTo(byteValue);
		assertThat(c.getByte("byte.allset", byteValueNegative)).isExactlyInstanceOf(Byte.class).isEqualTo(byteValue);
		assertThat(c.getByte("byte.wrap", byteValueNegative)).isExactlyInstanceOf(Byte.class).isEqualTo(byteValue);
	}

	@Test
	public void shortTest() {
		assertThat(c.getShort("short.first")).isExactlyInstanceOf(Short.class).isEqualTo(shortValue);
		assertThat(c.getShort("short.first.negative")).isExactlyInstanceOf(Short.class).isEqualTo(shortValueNegative);
		assertThat(c.getShort("short.wrap")).isExactlyInstanceOf(Short.class).isEqualTo(shortValue);
		assertThat(c.getShort("short.firstwrong")).isExactlyInstanceOf(Short.class).isEqualTo(shortValue);
		assertThat(c.getShort("short.allset")).isExactlyInstanceOf(Short.class).isEqualTo(shortValue);
		checkMissingKey(() -> c.getShort("missing.short"));
		assertThat(c.getShort("missing.short", shortValue)).isExactlyInstanceOf(Short.class).isEqualTo(shortValue);
		assertThat(c.getShort("short.allset", shortValueNegative)).isExactlyInstanceOf(Short.class).isEqualTo(shortValue);
		assertThat(c.getShort("short.wrap", shortValueNegative)).isExactlyInstanceOf(Short.class).isEqualTo(shortValue);
	}


	@Test
	public void intTest() {
		assertThat(c.getInteger("int.first")).isExactlyInstanceOf(Integer.class).isEqualTo(intValue);
		assertThat(c.getInteger("int.first.negative")).isExactlyInstanceOf(Integer.class).isEqualTo(intValueNegative);
		assertThat(c.getInteger("int.wrap")).isExactlyInstanceOf(Integer.class).isEqualTo(intValue);
		assertThat(c.getInteger("int.firstwrong")).isExactlyInstanceOf(Integer.class).isEqualTo(intValue);
		assertThat(c.getInteger("int.allset")).isExactlyInstanceOf(Integer.class).isEqualTo(intValue);
		checkMissingKey(() -> c.getInteger("missing.int"));
		assertThat(c.getInteger("missing.int", intValue)).isExactlyInstanceOf(Integer.class).isEqualTo(intValue);
		assertThat(c.getInteger("int.allset", intValueNegative)).isExactlyInstanceOf(Integer.class).isEqualTo(intValue);
		assertThat(c.getInteger("int.wrap", intValueNegative)).isExactlyInstanceOf(Integer.class).isEqualTo(intValue);
	}

	@Test
	public void longTest() {
		assertThat(c.getLong("long.first")).isExactlyInstanceOf(Long.class).isEqualTo(longValue);
		assertThat(c.getLong("long.first.negative")).isExactlyInstanceOf(Long.class).isEqualTo(longValueNegative);
		assertThat(c.getLong("long.wrap")).isExactlyInstanceOf(Long.class).isEqualTo(longValue);
		assertThat(c.getLong("long.firstwrong")).isExactlyInstanceOf(Long.class).isEqualTo(longValue);
		assertThat(c.getLong("long.allset")).isExactlyInstanceOf(Long.class).isEqualTo(longValue);
		checkMissingKey(() -> c.getLong("missing.long"));
		assertThat(c.getLong("missing.long", longValue)).isExactlyInstanceOf(Long.class).isEqualTo(longValue);
		assertThat(c.getLong("long.allset", longValueNegative)).isExactlyInstanceOf(Long.class).isEqualTo(longValue);
		assertThat(c.getLong("long.wrap", longValueNegative)).isExactlyInstanceOf(Long.class).isEqualTo(longValue);
	}

	@Test
	public void floatTest() {
		assertThat(c.getFloat("float.first")).isExactlyInstanceOf(Float.class).isEqualTo(floatValue);
		assertThat(c.getFloat("float.first.negative")).isExactlyInstanceOf(Float.class).isEqualTo(floatValueNegative);
		assertThat(c.getFloat("float.wrap")).isExactlyInstanceOf(Float.class).isEqualTo(floatValue);
		assertThat(c.getFloat("float.firstwrong")).isExactlyInstanceOf(Float.class).isEqualTo(floatValue);
		assertThat(c.getFloat("float.allset")).isExactlyInstanceOf(Float.class).isEqualTo(floatValue);
		checkMissingKey(() -> c.getFloat("missing.float"));
		assertThat(c.getFloat("missing.float", floatValue)).isExactlyInstanceOf(Float.class).isEqualTo(floatValue);
		assertThat(c.getFloat("float.allset", floatValueNegative)).isExactlyInstanceOf(Float.class).isEqualTo(floatValue);
		assertThat(c.getFloat("float.wrap", floatValueNegative)).isExactlyInstanceOf(Float.class).isEqualTo(floatValue);
	}

	@Test
	public void doubleTest() {
		assertThat(c.getDouble("double.first")).isExactlyInstanceOf(Double.class).isEqualTo(doubleValue);
		assertThat(c.getDouble("double.first.negative")).isExactlyInstanceOf(Double.class).isEqualTo(doubleValueNegative);
		assertThat(c.getDouble("double.wrap")).isExactlyInstanceOf(Double.class).isEqualTo(doubleValue);
		assertThat(c.getDouble("double.firstwrong")).isExactlyInstanceOf(Double.class).isEqualTo(doubleValue);
		assertThat(c.getDouble("double.allset")).isExactlyInstanceOf(Double.class).isEqualTo(doubleValue);
		checkMissingKey(() -> c.getDouble("missing.double"));
		assertThat(c.getDouble("missing.double", doubleValue)).isExactlyInstanceOf(Double.class).isEqualTo(doubleValue);
		assertThat(c.getDouble("double.allset", doubleValueNegative)).isExactlyInstanceOf(Double.class).isEqualTo(doubleValue);
		assertThat(c.getDouble("double.wrap", doubleValueNegative)).isExactlyInstanceOf(Double.class).isEqualTo(doubleValue);
	}

	@Test
	public void stringTests() {
		assertThat(c.getString("string.first")).isEqualTo(stringValue);
		assertThat(c.getString("string.wrap")).isEqualTo(stringValue);
		assertThat(c.getString("string.allset")).isEqualTo(stringValue);
		checkMissingKey(() -> c.getString("missing.string"));
		assertThat(c.getString("missing.string", stringValue)).isEqualTo(stringValue);
		assertThat(c.getString("string.allset", stringValueAnother)).isEqualTo(stringValue);
		assertThat(c.getString("string.wrap", stringValueAnother)).isEqualTo(stringValue);
	}

	@Test
	public void stringArrayTests() {
		assertThat(c.getStringArray("string.array.first")).isEqualTo(stringArrayValue);
		assertThat(c.getStringArray("string.array.wrap")).isEqualTo(stringArrayValue);
		assertThat(c.getStringArray("string.array.allset")).isEqualTo(stringArrayValue);
		checkMissingKey(() -> c.getString("missing.string.array"));
		assertThat(c.getStringArray("missing.string.array", stringArrayValue)).isEqualTo(stringArrayValue);
		assertThat(c.getStringArray("string.array.allset", stringArrayValueAnother)).isEqualTo(stringArrayValue);
		assertThat(c.getStringArray("string.array.wrap", stringArrayValueAnother)).isEqualTo(stringArrayValue);
	}
}