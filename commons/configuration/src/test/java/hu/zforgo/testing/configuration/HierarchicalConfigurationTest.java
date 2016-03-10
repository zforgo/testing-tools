package hu.zforgo.testing.configuration;

import org.junit.Test;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public abstract class HierarchicalConfigurationTest extends AbstractConfigurationTest {

	private static final String[] ITEMS = new String[]{"configuration/levels/first", "configuration/levels/first/second", "configuration/levels/first/second/third"};

	private static final String FILENAME = "hierarchical.properties";

	private static final String[] stringArrayValue = {"foo", "bar", "baz", "bad", "robot"};

	public HierarchicalConfigurationTest(String type, Configuration c) {
		super(type, c);
	}


	private static Configuration byClassLoader(String fileName, ClassLoader cl) throws IOException {
		Configuration result = null;
		List<String> configurations = Stream.of(ITEMS).collect(Collectors.toList());
		for (String c : configurations) {
			result = cl == null ? ConfigurationFactory.load(Paths.get(c, fileName).toString(), result) : ConfigurationFactory.load(Paths.get(c, fileName).toString(), cl, result);
		}
		return result;
	}

	private static Collection<Path> createPaths() {
		URL root = Thread.currentThread().getContextClassLoader().getResource("");
		assert root != null : "Couldn't find root url";
		return Stream.of(ITEMS).map(e -> {
			try {
				return Paths.get(root.toURI()).resolve(e);
			} catch (URISyntaxException e1) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
	}

	@Parameterized.Parameters
	public static Collection<Object[]> loadConfiguration() throws IOException, URISyntaxException {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		return Arrays.asList(new Object[][]{
				{"builtIn_classLoader", byClassLoader(FILENAME, null)},
				{"custom_classLoader", byClassLoader(FILENAME, cl)},
				{"path", ConfigurationFactory.load(FILENAME, createPaths())},
		});
	}

	@Test
	public void tryLoadMissing() throws URISyntaxException {
		{
			Throwable t = catchThrowable(() -> byClassLoader(MISSING_PROPERTIES_NAME, null));
			assertThat(t).isInstanceOf(IOException.class)
					.hasMessageStartingWith("Couldn't find properties file [").hasMessageContaining(MISSING_PROPERTIES_NAME);
		}
		{
			Throwable t = catchThrowable(() -> ConfigurationFactory.load(MISSING_PROPERTIES_NAME, createPaths()));
			assertThat(t).isInstanceOf(IOException.class).hasMessageStartingWith(String.format("Cannot load config file '%s' from sources:", MISSING_PROPERTIES_NAME));
		}
	}

	@Test
	public void submapTest() {
		Configuration sub = c.submap("string.array.");
		assertThat(sub.asMap().size()).isEqualTo(3);
		assertThat(sub.getString("must_not_be", null)).isNull();
		assertThat(sub.getString("string.allset", null)).isNull();
		assertThat(sub.getStringArray("allset")).isEqualTo(stringArrayValue);
		checkMissingKey(() -> sub.getString("string.allset"));
	}

	@Test
	public void remainsTest() {
		assertThat(c.remains("string.").asMap().size()).isEqualTo(34);
		assertThat(c.remains("string.array").asMap().size()).isEqualTo(37);
		assertThat(c.remains("string.array.").asMap().size()).isEqualTo(37);
		assertThat(c.remains("there_isnt_any").asMap().size()).isEqualTo(40);
	}
}
