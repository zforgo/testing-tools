package hu.zforgo.testing.configuration;


import hu.zforgo.common.util.CollectionUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class SimpleConfiguration implements Configuration {

	private Map<String, Object> props;

	public SimpleConfiguration(Properties props) {
		Objects.requireNonNull(props, "props must not be null");
		this.props = Collections.unmodifiableMap(props.entrySet().stream()
				.collect(Collectors.toMap(e -> String.valueOf(e.getKey()), Map.Entry::getValue)));
	}

	public SimpleConfiguration(Map<String, Object> props) {
		Objects.requireNonNull(props, "props must not be null");
		this.props = Collections.unmodifiableMap(props);
	}

	public boolean isEmpty() {
		return CollectionUtil.isEmpty(props);
	}

	@Override
	public Object get(String key) {
		Objects.requireNonNull(key, "Key cannot be null!");
		Object res = props.get(key);
		if (res == null) {
			throw new NoSuchElementException("Configuration key not found: " + key);
		}
		return res;
	}

	@Override
	public Configuration submap(String prefix) {
		Objects.requireNonNull(prefix, "Prefix cannot be null!");
		return new SimpleConfiguration(props.entrySet()
				.stream()
				.filter(p -> p.getKey().startsWith(prefix))
				.collect(Collectors.toMap(e -> e.getKey().substring(prefix.length()), Map.Entry::getValue)));
	}

	@Override
	public Configuration remains(String prefix) {
		Objects.requireNonNull(prefix, "Prefix cannot be null!");
		return new SimpleConfiguration(props.entrySet()
				.stream()
				.filter(p -> !p.getKey().startsWith(prefix))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
	}

	@Override
	public Map<String, Object> asMap() {
		return new HashMap<>(props);
	}
}
