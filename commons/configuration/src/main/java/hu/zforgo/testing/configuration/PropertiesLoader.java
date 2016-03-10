package hu.zforgo.testing.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

class PropertiesLoader {

	public static Properties load(final String name) throws IOException {
		return load(name, classLoader());
	}

	public static Properties load(final String name, final ClassLoader cl) throws IOException {
		try (InputStream is = cl.getResourceAsStream(name)) {
			if (is == null) {
				throw new IOException(String.format("Couldn't find properties file [%s] on classpath", name));
			}
			Properties p = new Properties();
			p.load(is);
			return p;
		}
	}

	public static Properties load(final String name, final Path path) throws IOException {
		final Path file = path.resolve(name);
		Properties p = new Properties();
		p.load(Files.newBufferedReader(file));
		return p;
	}

	private static ClassLoader classLoader() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) {
			cl = PropertiesLoader.class.getClassLoader();
			if (cl == null) {
				cl = ClassLoader.getSystemClassLoader();
			}
		}
		return cl;
	}
}
