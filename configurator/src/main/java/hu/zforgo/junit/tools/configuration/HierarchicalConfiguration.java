package hu.zforgo.junit.tools.configuration;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;

public class HierarchicalConfiguration extends AbstractConfiguration {

	private Configuration current;
	private Configuration parent;

	public HierarchicalConfiguration(Configuration current, Configuration parent) {
		Objects.requireNonNull(parent, "parent must not be null");
		Objects.requireNonNull(current, "current must not be null");

		this.current = current;
		this.parent = parent;
	}

	public HierarchicalConfiguration(Properties current, Configuration parent) {
		Objects.requireNonNull(parent, "parent must not be null");

		this.current = new SimlpleConfiguration(current);
		this.parent = parent;
	}

	public HierarchicalConfiguration(Map<String, Object> current, Configuration parent) {
		Objects.requireNonNull(parent, "parent must not be null");

		this.current = new SimlpleConfiguration(current);
		this.parent = parent;
	}

	@Override
	public Object get(String key) {
		Object result = current.get(key, null);
		if (result != null) {
			return result;
		} else if (parent != null) {
			return parent.get(key);
		}
		throw new NoSuchElementException("Configuration key not found: " + key);

	}
}
