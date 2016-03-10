package hu.zforgo.testing.context;

import hu.zforgo.testing.configuration.Configuration;

import java.nio.file.Path;
import java.util.List;

public interface ConfigurationPathProvider {

	List<Path> getPaths(final Path root, final Configuration props);
}
