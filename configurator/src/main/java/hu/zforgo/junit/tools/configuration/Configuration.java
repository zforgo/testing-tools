package hu.zforgo.junit.tools.configuration;

public interface Configuration {

	Object get(String key);

	Object get(String key, Object defaultValue);

	boolean boolValue(String key);

	boolean boolValue(String key, boolean defaultValue);

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

	String getString(String key);

	String getString(String key, String defaultValue);

}
