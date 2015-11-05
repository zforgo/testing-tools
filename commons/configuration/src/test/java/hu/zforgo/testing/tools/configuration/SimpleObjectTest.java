package hu.zforgo.testing.tools.configuration;

import hu.zforgo.common.util.StringUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleObjectTest extends SimpleConfigurationTest {

	private static final String stringValue = "Lorem ipsum color dem";
	private static final String stringValueDefault = "FooBar and Bad Robot";
	private static final String multilineString = "Bé ína helpá résti triliono. Jaro postpartö néi kz, ían post nuna semajntagó ad.\n" +
			" Sen ki finitivö memkomprenéble, nüna vivuí apostrofo du des. Tian gramatíka iv eca, álies kromakcento suprenstreko esk sí.\n" +
			"Ge péta kaŭzé kvádriliónó san, öré kö infinitivo ánstataŭigi.\n" +
			" It nia kuzo móno frazmelodio, úl trema tútámpleksa tia. Ab esk egalas tagnoktő, in tre kvar nuancado afganistano.\n" +
			" Nj túj veki komplika júgoslavö, be nén lasta prézoinda neőficiála. Ehe rípeti ánstátaŭa úm.\n" +
			" Os dekumi próksimumecó bis, okcidénte salutfrazo if nűr.";

	private static final String[] stringArray = new String[]{"foo", "bar", "baz", "bad", "robot"};
	private static final String[] stringArrayDouble = new String[]{"foo", "", "bar", "baz", "bad", "robot"};
	private static final String[] stringArrayMultiline = new String[]{
			"Bé ína helpá résti triliono",
			"Jaro postpartö néi kz, ían post nuna semajntagó ad",
			"Sen ki finitivö memkomprenéble, nüna vivuí apostrofo du des",
			"Tian gramatíka iv eca, álies kromakcento suprenstreko esk sí",
			"Ge péta kaŭzé kvádriliónó san, öré kö infinitivo ánstataŭigi"
	};

	public SimpleObjectTest(String type, Configuration c) {
		super(type, c);
	}

	@Test
	public void booleanTests() {
		assertThat(c.getBoolean("basic.boolean")).isExactlyInstanceOf(Boolean.class).isTrue();
		assertThat(c.getBoolean("numeric.boolean")).isExactlyInstanceOf(Boolean.class).isTrue();
		assertThat(c.getBoolean("numeric.boolean.number")).isExactlyInstanceOf(Boolean.class).isFalse();
		assertThat(c.getBoolean("numeric.boolean.false")).isExactlyInstanceOf(Boolean.class).isFalse();
		checkMissingKey(() -> c.getBoolean("missing.boolean"));
		assertThat(c.getBoolean("basic.boolean", false)).isExactlyInstanceOf(Boolean.class).isTrue();
		assertThat(c.getBoolean("missing.boolean", true)).isExactlyInstanceOf(Boolean.class).isTrue();
		assertThat(c.getBoolean("missing.boolean", false)).isExactlyInstanceOf(Boolean.class).isFalse();

		assertThat(c.getBoolean("missing.boolean", null)).isNull();
	}

	@Test
	public void byteTests() {
		assertThat(c.getByte("byte.value")).isExactlyInstanceOf(Byte.class).isEqualTo(byteValue);
		assertThat(c.getByte("byte.value.negative")).isExactlyInstanceOf(Byte.class).isEqualTo(byteValueNegative);
		checkMissingKey(() -> c.getByte("missing.byte"));
		assertThat(c.getByte("byte.value", byteDefaultValue)).isExactlyInstanceOf(Byte.class).isEqualTo(byteValue);
		assertThat(c.getByte("byte.value.negative", byteDefaultValue)).isExactlyInstanceOf(Byte.class).isEqualTo(byteValueNegative);
		assertThat(c.getByte("missing.byte", byteDefaultValue)).isExactlyInstanceOf(Byte.class).isEqualTo(byteDefaultValue);
		checkInvalidNumber(() -> c.getByte("string.value"));
		checkInvalidNumber(() -> c.getByte("string.value", byteDefaultValue));
		checkInvalidNumber(() -> c.getByte("float.value.low"));
		checkInvalidNumberRange(() -> c.getByte("int.value"));

		assertThat(c.getByte("missing.byte", null)).isNull();
	}

	@Test
	public void shortTests() {
		assertThat(c.getShort("byte.value")).isExactlyInstanceOf(Short.class).isEqualTo(byteValue);
		assertThat(c.getShort("byte.value.negative")).isExactlyInstanceOf(Short.class).isEqualTo(byteValueNegative);
		assertThat(c.getShort("short.value")).isExactlyInstanceOf(Short.class).isEqualTo(shortValue);
		assertThat(c.getShort("short.value.negative")).isExactlyInstanceOf(Short.class).isEqualTo(shortValueNegative);
		checkMissingKey(() -> c.getShort("missing.short"));
		assertThat(c.getShort("missing.short", shortDefaultValue)).isExactlyInstanceOf(Short.class).isEqualTo(shortDefaultValue);
		checkInvalidNumber(() -> c.getShort("string.value"));
		checkInvalidNumber(() -> c.getShort("string.value", shortDefaultValue));
		checkInvalidNumber(() -> c.getShort("float.value.low"));
		checkInvalidNumberRange(() -> c.getShort("int.value"));

		assertThat(c.getShort("missing.short", null)).isNull();
	}

	@Test
	public void integerTests() {
		assertThat(c.getInteger("byte.value")).isExactlyInstanceOf(Integer.class).isEqualTo(byteValue);
		assertThat(c.getInteger("byte.value.negative")).isExactlyInstanceOf(Integer.class).isEqualTo(byteValueNegative);
		assertThat(c.getInteger("short.value")).isExactlyInstanceOf(Integer.class).isEqualTo(shortValue);
		assertThat(c.getInteger("short.value.negative")).isExactlyInstanceOf(Integer.class).isEqualTo(shortValueNegative);
		assertThat(c.getInteger("short.value")).isExactlyInstanceOf(Integer.class).isEqualTo(shortValue);
		assertThat(c.getInteger("short.value.negative")).isExactlyInstanceOf(Integer.class).isEqualTo(shortValueNegative);
		assertThat(c.getInteger("int.value")).isExactlyInstanceOf(Integer.class).isEqualTo(intValue);
		assertThat(c.getInteger("int.value.negative")).isExactlyInstanceOf(Integer.class).isEqualTo(intValueNegative);
		checkMissingKey(() -> c.getInteger("missing.int"));
		assertThat(c.getInteger("missing.int", intDefaultValue)).isExactlyInstanceOf(Integer.class).isEqualTo(intDefaultValue);
		checkInvalidNumber(() -> c.getInteger("string.value"));
		checkInvalidNumber(() -> c.getInteger("string.value", intDefaultValue));
		checkInvalidNumber(() -> c.getInteger("float.value.low"));

		/*
		Must be checked by checkInvalidNumber because
		Integer.parseInt thorws NFEx with 'For input string:'
		message instead of 'Value out of range.'
		*/
		checkInvalidNumber(() -> c.getInteger("long.value"));

		assertThat(c.getInteger("missing.integer", null)).isNull();
	}

	@Test
	public void longTest() {
		assertThat(c.getLong("byte.value")).isExactlyInstanceOf(Long.class).isEqualTo(byteValue);
		assertThat(c.getLong("byte.value.negative")).isExactlyInstanceOf(Long.class).isEqualTo(byteValueNegative);
		assertThat(c.getLong("short.value")).isExactlyInstanceOf(Long.class).isEqualTo(shortValue);
		assertThat(c.getLong("short.value.negative")).isExactlyInstanceOf(Long.class).isEqualTo(shortValueNegative);
		assertThat(c.getLong("short.value")).isExactlyInstanceOf(Long.class).isEqualTo(shortValue);
		assertThat(c.getLong("short.value.negative")).isExactlyInstanceOf(Long.class).isEqualTo(shortValueNegative);
		assertThat(c.getLong("int.value")).isExactlyInstanceOf(Long.class).isEqualTo(intValue);
		assertThat(c.getLong("int.value.negative")).isExactlyInstanceOf(Long.class).isEqualTo(intValueNegative);

		checkMissingKey(() -> c.getLong("missing.long"));
		assertThat(c.getLong("missing.long", longDefaultValue)).isExactlyInstanceOf(Long.class).isEqualTo(longDefaultValue);
		assertThat(c.getLong("long.max.value", Long.MIN_VALUE)).isExactlyInstanceOf(Long.class).isEqualTo(Long.MAX_VALUE);
		assertThat(c.getLong("long.min.value", Long.MAX_VALUE)).isExactlyInstanceOf(Long.class).isEqualTo(Long.MIN_VALUE);

		assertThat(c.getLong("long.min.value")).isExactlyInstanceOf(Long.class).isEqualTo(Long.MIN_VALUE);
		assertThat(c.getLong("long.max.value")).isExactlyInstanceOf(Long.class).isEqualTo(Long.MAX_VALUE);

		checkInvalidNumber(() -> c.getLong("string.value"));
		checkInvalidNumber(() -> c.getLong("string.value", longDefaultValue));
		checkInvalidNumber(() -> c.getLong("float.value.low"));
		checkInvalidNumber(() -> c.getLong("long.value.withmark"));
		checkInvalidNumber(() -> c.getLong("long.min.value.withspace"));
		checkInvalidNumber(() -> c.getLong("float.value"));

		assertThat(c.getLong("missing.long", null)).isNull();
	}

	@Test
	public void floatTest() {
		assertThat(c.getFloat("byte.value")).isExactlyInstanceOf(Float.class).isEqualTo(byteValue);
		assertThat(c.getFloat("byte.value.negative")).isExactlyInstanceOf(Float.class).isEqualTo(byteValueNegative);
		assertThat(c.getFloat("short.value")).isExactlyInstanceOf(Float.class).isEqualTo(shortValue);
		assertThat(c.getFloat("short.value.negative")).isExactlyInstanceOf(Float.class).isEqualTo(shortValueNegative);
		assertThat(c.getFloat("short.value")).isExactlyInstanceOf(Float.class).isEqualTo(shortValue);
		assertThat(c.getFloat("short.value.negative")).isExactlyInstanceOf(Float.class).isEqualTo(shortValueNegative);
		assertThat(c.getFloat("int.value")).isExactlyInstanceOf(Float.class).isEqualTo(intValue);
		assertThat(c.getFloat("int.value.negative")).isExactlyInstanceOf(Float.class).isEqualTo(intValueNegative);
		assertThat(c.getFloat("long.min.value")).isExactlyInstanceOf(Float.class).isEqualTo(Long.MIN_VALUE);
		assertThat(c.getFloat("long.max.value")).isExactlyInstanceOf(Float.class).isEqualTo(Long.MAX_VALUE);

		checkMissingKey(() -> c.getFloat("missing.float"));
		assertThat(c.getFloat("missing.float", floatValueLow)).isExactlyInstanceOf(Float.class).isEqualTo(floatValueLow);
		assertThat(c.getFloat("float.value")).isExactlyInstanceOf(Float.class).isEqualTo(floatValue);
		assertThat(c.getFloat("float.value.hasdecimals")).isExactlyInstanceOf(Float.class).isEqualTo(floatValueHasdecimals);
		assertThat(c.getFloat("float.max.value")).isExactlyInstanceOf(Float.class).isEqualTo(Float.MAX_VALUE);
//TODO negative float check

		assertThat(Float.isInfinite(c.getFloat("double.value"))).isTrue();


		checkInvalidNumber(() -> c.getFloat("string.value"));
		checkInvalidNumber(() -> c.getFloat("string.value", floatValue));

		assertThat(c.getFloat("missing.float", null)).isNull();
	}


	@Test
	public void doubleTest() {
		assertThat(c.getDouble("byte.value")).isExactlyInstanceOf(Double.class).isEqualTo(byteValue);
		assertThat(c.getDouble("byte.value.negative")).isExactlyInstanceOf(Double.class).isEqualTo(byteValueNegative);
		assertThat(c.getDouble("short.value")).isExactlyInstanceOf(Double.class).isEqualTo(shortValue);
		assertThat(c.getDouble("short.value.negative")).isExactlyInstanceOf(Double.class).isEqualTo(shortValueNegative);
		assertThat(c.getDouble("int.value")).isExactlyInstanceOf(Double.class).isEqualTo(intValue);
		assertThat(c.getDouble("int.value.negative")).isExactlyInstanceOf(Double.class).isEqualTo(intValueNegative);
		assertThat(c.getDouble("long.min.value")).isExactlyInstanceOf(Double.class).isEqualTo(Long.MIN_VALUE);
		assertThat(c.getDouble("long.max.value")).isExactlyInstanceOf(Double.class).isEqualTo(Long.MAX_VALUE);
		assertThat(c.getDouble("float.value")).isExactlyInstanceOf(Double.class).isEqualTo(floatValueAsDouble);

		checkMissingKey(() -> c.getDouble("missing.double"));
		assertThat(c.getDouble("missing.double", floatValueAsDouble)).isExactlyInstanceOf(Double.class).isEqualTo(floatValueAsDouble);
		assertThat(c.getDouble("double.value")).isExactlyInstanceOf(Double.class).isEqualTo(doubleValue);
//TODO negative double checks
//		assertThat(c.getDouble("double.value.negative")).isExactlyInstanceOf(Double.class).isEqualTo(doubleValueNegative);


		checkInvalidNumber(() -> c.getDouble("string.value"));
		checkInvalidNumber(() -> c.getDouble("string.value", doubleValue));

		assertThat(c.getDouble("missing.double", null)).isNull();

	}

	@Test
	public void stringTest() {
		assertThat(c.getString("string.value")).isEqualTo(stringValue);
		assertThat(c.getString("string.value", stringValueDefault)).isEqualTo(stringValue);
		checkMissingKey(() -> c.getString("missing.string"));
		assertThat(c.getString("missing.string", stringValueDefault)).isEqualTo(stringValueDefault);
		assertThat(c.getString("string.value.multiline", multilineString)).isEqualTo(multilineString);
		assertThat(c.getString("string.missing.value")).isEmpty();
		assertThat(c.getString("string.empty.value")).isEmpty();
		assertThat(c.getString("string.value.leading")).isEqualTo(stringValue);
		assertThat(c.getString("string.value.leading.newline")).isEqualTo(stringValue);
	}

	@Test
	public void stringArrayTest() {
		assertThat(c.getStringArray("string.array")).isEqualTo(stringArray);
		assertThat(c.getStringArray("string.array", StringUtil.EMPTY_STRING_ARRAY)).isEqualTo(stringArray);
		checkMissingKey(() -> c.getString("missing.string.array"));
		assertThat(c.getStringArray("missing.string.array", stringArray)).isEqualTo(stringArray);
		assertThat(c.getStringArray("string.array.double.semicolon")).isEqualTo(stringArrayDouble);
		assertThat(c.getStringArray("string.array.trailing.semicolon")).isEqualTo(stringArray);
		assertThat(c.getStringArray("string.array.multiline")).isEqualTo(stringArrayMultiline);

		assertThat(c.getStringArray("string.array.colon", ':')).isEqualTo(stringArray);
		assertThat(c.getStringArray("string.array.colon", ':', StringUtil.EMPTY_STRING_ARRAY)).isEqualTo(stringArray);
		assertThat(c.getStringArray("string.array.double.colon", ':')).isEqualTo(stringArrayDouble);
		assertThat(c.getStringArray("string.array.colon.escaped", ':')).isEqualTo(stringArray);
		assertThat(c.getStringArray("string.array.colon.multiline", ':')).isEqualTo(stringArrayMultiline);
	}
}
