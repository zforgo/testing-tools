package hu.zforgo.junit.tools.context;

import hu.zforgo.ClassUtil;
import hu.zforgo.junit.tools.configuration.Configuration;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

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
}
