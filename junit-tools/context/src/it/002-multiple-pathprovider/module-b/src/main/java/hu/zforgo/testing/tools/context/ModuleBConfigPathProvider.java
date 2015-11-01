package hu.zforgo.testing.tools.context;

import hu.zforgo.testing.context.ConfigurationPathProvider;
import hu.zforgo.testing.tools.configuration.Configuration;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleBConfigPathProvider implements ConfigurationPathProvider {

	@Override
	public List<Path> getPaths(final Path root, Configuration props) {
		return Stream.of("moduleB/foo", "moduleB/foo/bar", "moduleB/foo/bar/baz").map(root::resolve).collect(Collectors.toList());
	}
}