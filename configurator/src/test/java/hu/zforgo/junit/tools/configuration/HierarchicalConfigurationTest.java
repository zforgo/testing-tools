package hu.zforgo.junit.tools.configuration;

import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HierarchicalConfigurationTest extends AbstractConfigurationTest {

	private static final String[] ITEMS = new String[]{"configuration/levels/first", "configuration/levels/first/second", "configuration/levels/first/second/third"};

	private static final String FILENAME = "hierarchical.properties";

	public HierarchicalConfigurationTest(String type, Configuration c) {
		super(type, c);
	}


	private static Configuration byClassLoader(ClassLoader cl) throws IOException {
		Configuration result = null;
		List<String> configurations = Stream.of(ITEMS).collect(Collectors.toList());
		for (String c : configurations) {
			result = cl == null ? ConfigurationFactory.load(Paths.get(c, FILENAME).toString(), result) : ConfigurationFactory.load(Paths.get(c, FILENAME).toString(), cl, result);
		}
		return result;
	}

	private static Configuration byPath() throws IOException, URISyntaxException {
		URL root = Thread.currentThread().getContextClassLoader().getResource("");
		assert root != null : "Couldn't find root url";

		return ConfigurationFactory.load(FILENAME, Stream.of(ITEMS).map(e -> {
			try {
				return Paths.get(root.toURI()).resolve(e);
			} catch (URISyntaxException e1) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList()));
	}

	@Parameterized.Parameters
	public static Collection<Object[]> loadConfiguration() throws IOException, URISyntaxException {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		return Arrays.asList(new Object[][]{
				{"builtIn_classLoader", byClassLoader(null)},
				{"custom_classLoader", byClassLoader(cl)},
				{"path", byPath()},
		});
	}
}
