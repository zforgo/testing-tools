package hu.zforgo.junit.tools.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertiesLoader {

	public static Properties load(final String name) throws IOException {
		return load(classLoader(), name);
	}

	public static Properties load(final ClassLoader cl, final String name) throws IOException {
		try (InputStream is = cl.getResourceAsStream(name)) {
			Properties p = new Properties();
			p.load(is);
			return p;
		}
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
