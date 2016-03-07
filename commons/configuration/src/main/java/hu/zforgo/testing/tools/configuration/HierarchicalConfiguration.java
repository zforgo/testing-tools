package hu.zforgo.testing.tools.configuration;

import hu.zforgo.common.util.CollectionUtil;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;

public class HierarchicalConfiguration implements Configuration {

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

		this.current = new SimpleConfiguration(current);
		this.parent = parent;
	}

	public HierarchicalConfiguration(Map<String, Object> current, Configuration parent) {
		Objects.requireNonNull(parent, "parent must not be null");

		this.current = new SimpleConfiguration(current);
		this.parent = parent;
	}

	public boolean isEmpty() {
		return current.isEmpty() && (parent == null || parent.isEmpty());
	}

	@Override
	public Object get(String key) {
		Objects.requireNonNull(key, "Key cannot be null!");
		Object result = current.get(key, null);
		if (result != null) {
			return result;
		} else if (parent != null) {
			return parent.get(key);
		}
		throw new NoSuchElementException("Configuration key not found: " + key);
	}

	@Override
	public Configuration submap(String prefix) {
		Objects.requireNonNull(prefix, "Prefix cannot be null!");
		return new HierarchicalConfiguration(
				current.submap(prefix), parent.submap(prefix));
	}

	@Override
	public Configuration remains(String prefix) {
		Objects.requireNonNull(prefix, "Prefix cannot be null!");
		return new HierarchicalConfiguration(
				current.remains(prefix), parent.remains(prefix));
	}

	@Override
	public Map<String, Object> asMap() {
		return CollectionUtil.unionMap(parent.asMap(), current.asMap());
	}
}
