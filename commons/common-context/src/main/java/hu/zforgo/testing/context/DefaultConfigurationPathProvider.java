package hu.zforgo.testing.context;

import hu.zforgo.testing.configuration.Configuration;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public final class DefaultConfigurationPathProvider implements ConfigurationPathProvider {

	private final List<Path> paths;

	public DefaultConfigurationPathProvider(Path root) {
		paths = Collections.singletonList(root);
	}

	@Override
	public List<Path> getPaths(Path root, Configuration props) {
		return paths;
	}
}
