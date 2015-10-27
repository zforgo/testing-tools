package hu.zforgo.testing.context;

import hu.zforgo.testing.tools.configuration.Configuration;

import java.nio.file.Path;
import java.util.List;

public interface ConfigurationPathProvider {

	List<Path> getPaths(final Path root, final Configuration props);
}
