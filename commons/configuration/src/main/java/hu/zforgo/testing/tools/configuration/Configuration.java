package hu.zforgo.testing.tools.configuration;

import hu.zforgo.common.util.StringUtil;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Configuration {

	Set<String> BOOLEAN_LIKE = Stream.of("enable", "enabled", "on", "1").collect(Collectors.toSet());

	Configuration EMPTY = new Configuration() {
		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public Configuration submap(String prefix) {
			return this;
		}

		@Override
		public Configuration remains(String prefix) {
			return this;
		}

		@Override
		public Map<String, Object> asMap() {
			return Collections.emptyMap();
		}

		@Override
		public Object get(String key) {
			throw new NoSuchElementException("Configuration key not found: " + key);
		}
	};

	default <T> T safe(Supplier<T> c, T defaultValue) {
		try {
			return c.get();
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	boolean isEmpty();

	Configuration submap(String prefix);

	Configuration remains(String prefix);

	Map<String, Object> asMap();

	Object get(String key);

	default Object get(String key, Object defaultValue) {
		return safe(() -> get(key), defaultValue);
	}

	default boolean boolValue(String key) {
		Object o = get(key);
		return o != null && ((o instanceof String && BOOLEAN_LIKE.contains(((String) o).trim().toLowerCase())) || Boolean.parseBoolean(o.toString().trim()));
	}

	default boolean boolValue(String key, boolean defaultValue) {
		return safe(() -> boolValue(key), defaultValue);
	}

	default Boolean getBoolean(String key) {
		return boolValue(key);
	}

	default Boolean getBoolean(String key, Boolean defaultValue) {
		return safe(() -> getBoolean(key), defaultValue);
	}

	default byte byteValue(String key) {
		return Byte.parseByte(get(key).toString().trim());
	}

	default byte byteValue(String key, byte defaultValue) {
		return safe(() -> byteValue(key), defaultValue);
	}

	default short shortValue(String key) {
		return Short.parseShort(get(key).toString().trim());
	}

	default short shortValue(String key, short defaultValue) {
		return safe(() -> shortValue(key), defaultValue);
	}

	default int intValue(String key) {
		return Integer.parseInt(get(key).toString().trim());
	}

	default int intValue(String key, int defaultValue) {
		return safe(() -> intValue(key), defaultValue);
	}

	default long longValue(String key) {
		return Long.parseLong(get(key).toString().trim());
	}

	default long longValue(String key, long defaultValue) {
		return safe(() -> longValue(key), defaultValue);
	}

	default float floatValue(String key) {
		return Float.parseFloat(get(key).toString().trim());
	}

	default float floatValue(String key, float defaultValue) {
		return safe(() -> floatValue(key), defaultValue);
	}

	default double doubleValue(String key) {
		return Double.parseDouble(get(key).toString().trim());
	}

	default double doubleValue(String key, double defaultValue) {
		return safe(() -> doubleValue(key), defaultValue);
	}

	default Byte getByte(String key) {
		return Byte.valueOf(getString(key));
	}

	default Byte getByte(String key, Byte defaultValue) {
		return safe(() -> getByte(key), defaultValue);
	}

	default Short getShort(String key) {
		return Short.valueOf(getString(key));
	}

	default Short getShort(String key, Short defaultValue) {
		return safe(() -> getShort(key), defaultValue);
	}

	default Integer getInteger(String key) {
		return Integer.valueOf(getString(key));
	}

	default Integer getInteger(String key, Integer defaultValue) {
		return safe(() -> getInteger(key), defaultValue);
	}

	default Long getLong(String key) {
		return Long.valueOf(getString(key));
	}

	default Long getLong(String key, Long defaultValue) {
		return safe(() -> getLong(key), defaultValue);
	}

	default Float getFloat(String key) {
		return Float.valueOf(getString(key));
	}

	default Float getFloat(String key, Float defaultValue) {
		return safe(() -> getFloat(key), defaultValue);
	}

	default Double getDouble(String key) {
		return Double.valueOf(getString(key));
	}

	default Double getDouble(String key, Double defaultValue) {
		return safe(() -> getDouble(key), defaultValue);
	}

	default String getString(String key) {
		return String.valueOf(get(key));
	}

	default String getString(String key, String defaultValue) {
		return safe(() -> getString(key), defaultValue);
	}


	default String[] getStringArray(String key) {
		return getStringArray(key, StringUtil.DEFAULT_DELIMITER);
	}

	default String[] getStringArray(String key, char delimiter) {
		return getString(key).split(String.valueOf(delimiter));
	}

	default String[] getStringArray(String key, String[] defaultValue) {
		return safe(() -> getStringArray(key), defaultValue);
	}

	default String[] getStringArray(String key, char delimiter, String[] defaultValue) {
		return safe(() -> getStringArray(key, delimiter), defaultValue);
	}

	default <E extends Enum<E>> E getEnum(String key, Class<E> type) {
		String value = getString(key);
		return Enum.valueOf(type, value);
	}

	default <E extends Enum<E>> E getEnum(String key, Class<E> type, E defaultValue) {
		return safe(() -> getEnum(key, type), defaultValue);
	}

	default <E extends Enum<E>> E getEnum(String key, E defaultValue) {
		return safe(() -> getEnum(key, defaultValue.getDeclaringClass()), defaultValue);
	}

}
