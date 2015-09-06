package hu.zforgo.junit.tools.configuration;

import hu.zforgo.CollectionUtil;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class SimlpleConfiguration extends AbstractConfiguration {

	private Map<String, Object> props;

	public SimlpleConfiguration(Properties props) {
		Objects.requireNonNull(props, "props must not be null");
		this.props = props.entrySet().stream()
				.collect(Collectors.toMap(e -> String.valueOf(e.getKey()), Map.Entry::getValue));
	}

	public SimlpleConfiguration(Map<String, Object> props) {
		Objects.requireNonNull(props, "props must not be null");
		this.props = props;
	}

	public boolean isEmpty() {
		return CollectionUtil.isEmpty(props);
	}

	@Override
	public Object get(String key) {
		Object res = props.get(key);
		if (res == null) {
			throw new NoSuchElementException("Configuration key not found: " + key);
		}
		return res;
	}
}
