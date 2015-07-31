package hu.zforgo.junit.tools.configuration;


import java.io.IOException;
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

}
