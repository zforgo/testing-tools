package hu.zforgo.junit.tools.configuration;


import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Properties;

public class ConfigurationFactory {

	public static Configuration load(String name) throws IOException {
		Properties p = PropertiesLoader.load(name);
		return new SimlpleConfiguration(p);
	}


	public static Configuration load(String name, ClassLoader cl) throws IOException {
		Properties p = PropertiesLoader.load(cl, name);
		return new SimlpleConfiguration(p);
	}

	public static Configuration load(String name, Configuration parent) throws IOException {
		Configuration base = load(name);
		return new HierarchicalConfiguration(base, parent);
	}

	public static Configuration load(String name, ClassLoader cl, Configuration parent) throws IOException {
		Configuration base = load(name, cl);
		return new HierarchicalConfiguration(base, parent);
	}

	public static Configuration load(String name, Collection<Path> paths) throws IOException {
		Configuration c = null;
		for (Path path : paths) {
			try {
				Properties p = PropertiesLoader.load(name, path);
				if (p != null && !p.isEmpty()) {
					c = (c == null) ? new SimlpleConfiguration(p) : new HierarchicalConfiguration(p, c);
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

}
