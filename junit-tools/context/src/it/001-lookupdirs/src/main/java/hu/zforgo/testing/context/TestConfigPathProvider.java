package hu.zforgo.testing.context;


import hu.zforgo.testing.configuration.Configuration;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestConfigPathProvider implements ConfigurationPathProvider {

	@Override
	public List<Path> getPaths(final Path root, Configuration props) {
		return Stream.of("foo", "foo/bar", "foo/bar/baz").map(root::resolve).collect(Collectors.toList());
	}
}
