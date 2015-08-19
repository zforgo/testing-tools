package hu.zforgo.junit.tools.context;

import hu.zforgo.junit.tools.configuration.Configuration;

import java.nio.file.Path;
import java.util.Set;

public interface ConfigurationPathProvider {

	Set<Path> getPaths(final Path root, final Configuration props);
}
