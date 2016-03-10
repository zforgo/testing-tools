package hu.zforgo.testing.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HierarchicalObjectTest extends HierarchicalConfigurationTest {

	private static final String stringValue = "Lorem ipsum color dem";
	private static final String stringValueAnother = "Another String";
	private static final String[] stringArrayValue = {"foo", "bar", "baz", "bad", "robot"};
	private static final String[] stringArrayValueAnother = {"another", "string"};

	public HierarchicalObjectTest(String type, Configuration c) {
		super(type, c);
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