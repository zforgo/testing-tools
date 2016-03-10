package hu.zforgo.testing.configuration;


import hu.zforgo.common.util.CollectionUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Properties;

public class ConfigurationFactory {

	public static Configuration load(String name) throws IOException {
		Properties p = PropertiesLoader.load(name);
		return new SimpleConfiguration(p);
	}

	public static Configuration load(String name, ClassLoader cl) throws IOException {
		Properties p = PropertiesLoader.load(name, cl);
		return new SimpleConfiguration(p);
	}

	public static Configuration load(String name, Path path) throws IOException {
		Properties p = PropertiesLoader.load(name, path);
		return new SimpleConfiguration(p);
	}

	public static Configuration load(String name, Configuration parent) throws IOException {
		Configuration base;
		try {
			base = load(name);
		} catch (IOException e) {
			if (parent == null) {
				throw e;
			}
			return parent;
		}
		return (parent == null) ? base : new HierarchicalConfiguration(base, parent);

	}

	public static Configuration load(String name, ClassLoader cl, Configuration parent) throws IOException {
		Configuration base = load(name, cl);
		return (parent == null) ? base : new HierarchicalConfiguration(base, parent);
	}

	public static Configuration load(String name, Collection<Path> paths) throws IOException {
		Configuration c = null;
		for (Path path : paths) {
			try {
				Properties p = PropertiesLoader.load(name, path);
				if (p != null && !p.isEmpty()) {
					c = (c == null) ? new SimpleConfiguration(p) : new HierarchicalConfiguration(p, c);
				}
			} catch (IOException ignored) {
			}
		}
		if (c == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("Cannot load config file '").append(name).append("' from sources:");
			paths.forEach(path -> sb.append(path.toString()).append("\n"));
			throw new IOException(sb.toString());
		}
		return c;
	}

	public static Configuration load(String name, Collection<Path> paths, Configuration parent) throws IOException {
		try {
			Configuration c = load(name, paths);
			return c == null || c.isEmpty() ? parent : new HierarchicalConfiguration(c, parent);
		} catch (IOException e) {
			return parent;
		}
	}

	public static Configuration merge(Configuration... configurations) {
		if (CollectionUtil.isEmpty(configurations)) {
			return Configuration.EMPTY;
		}
		if (configurations.length == 1) {
			return configurations[0];
		}

		Configuration result = configurations[0];
		for (int i = 1; i < configurations.length; i++) {
			result = new HierarchicalConfiguration(result, configurations[i]);
		}
		return result;
	}
}
