package hu.zforgo.junit.tools.context;

import hu.zforgo.common.util.ClassUtil;
import hu.zforgo.testing.context.Defaults;
import hu.zforgo.testing.tools.configuration.Configuration;
import hu.zforgo.testing.tools.configuration.SimpleConfiguration;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class SimpleStartupTest {

	@Test
	public void simpleTest() throws NoSuchFieldException {
		List<Path> ps = ClassUtil.fieldValue(JUnitToolsContext.class, "configLookupFolders");

		final Path root = Paths.get(System.getProperty(Defaults.LOOKUP_ROOT));
		List<Path> expected = Stream.of("foo", "foo/bar", "foo/bar/baz").map(root::resolve).collect(Collectors.toList());

		assertThat(ps).isEqualTo(expected);

		Configuration c = JUnitToolsContext.getInstance().getConfig("test.properties");
		assertThat(c.getString("relative.path")).isEqualTo("foo/bar/baz");
		assertThat(c.getString("middle.only")).isEqualTo("herewego");
		assertThat(c.getString("wrapped")).isEqualTo("third");
	}

	@Test
	public void defaultConfigTest() {
		{
			Configuration c = JUnitToolsContext.getInstance().getConfig("missing.properties", Configuration.EMPTY);
			assertThat(c).isEqualTo(Configuration.EMPTY);
			checkMissingKey(() -> c.getString("relative.path"));
			checkMissingKey(() -> c.getString("middle.only"));
			checkMissingKey(() -> c.getString("wrapped"));
			assertThat(c.isEmpty()).isTrue();
		}

		{
			Properties tmp = new Properties();
			tmp.setProperty("foo", "bar");
			tmp.setProperty("true.boolean", "1");
			Configuration expected = new SimpleConfiguration(tmp);

			Configuration c = JUnitToolsContext.getInstance().getConfig("missing.properties", expected);
			assertThat(c).isEqualTo(expected);
			checkMissingKey(() -> c.getString("relative.path"));
			assertThat(c.getString("foo")).isEqualTo("bar");
			assertThat(c.boolValue("true.boolean")).isTrue();
			assertThat(c.isEmpty()).isFalse();
		}
	}

	private void checkMissingKey(final ThrowableAssert.ThrowingCallable e) {
		Throwable t = catchThrowable(e);
		assertThat(t).isInstanceOf(NoSuchElementException.class)
				.hasMessageStartingWith("Configuration key not found: ");
	}

}
