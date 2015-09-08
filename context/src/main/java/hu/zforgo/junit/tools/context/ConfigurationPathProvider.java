package hu.zforgo.junit.tools.context;

import hu.zforgo.junit.tools.configuration.Configuration;

import java.nio.file.Path;
import java.util.List;

public interface ConfigurationPathProvider {

	List<Path> getPaths(final Path root, final Configuration props);
}
