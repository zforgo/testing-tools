package hu.zforgo.testing.tools.configuration;

import java.util.NoSuchElementException;

public interface Configuration {

	Configuration EMPTY = new AbstractConfiguration() {
		@Override
		public Object get(String key) {
			throw new NoSuchElementException("Configuration key not found: " + key);
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public Configuration submap(String prefix) {
			return this;
		}
	};

	boolean isEmpty();

	Configuration submap(String prefix);

	Object get(String key);

	Object get(String key, Object defaultValue);

	boolean boolValue(String key);

	boolean boolValue(String key, boolean defaultValue);

	Boolean getBoolean(String key);

	Boolean getBoolean(String key, Boolean defaultValue);

	byte byteValue(String key);

	byte byteValue(String key, byte defaultValue);

	short shortValue(String key);

	short shortValue(String key, short defaultValue);

	int intValue(String key);

	int intValue(String key, int defaultValue);

	long longValue(String key);

	long longValue(String key, long defaultValue);

	float floatValue(String key);

	float floatValue(String key, float defaultValue);

	double doubleValue(String key);

	double doubleValue(String key, double defaultValue);

	Byte getByte(String key);

	Byte getByte(String key, Byte defaultValue);

	Short getShort(String key);

	Short getShort(String key, Short defaultValue);

	Integer getInteger(String key);

	Integer getInteger(String key, Integer defaultValue);

	Long getLong(String key);

	Long getLong(String key, Long defaultValue);

	Float getFloat(String key);

	Float getFloat(String key, Float defaultValue);

	Double getDouble(String key);

	Double getDouble(String key, Double defaultValue);

	String getString(String key);

	String getString(String key, String defaultValue);

	String[] getStringArray(String key);

	String[] getStringArray(String key, char delimiter);

	String[] getStringArray(String key, String[] defaultValue);

	String[] getStringArray(String key, char delimiter, String[] defaultValue);

}
