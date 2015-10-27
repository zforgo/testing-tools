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
