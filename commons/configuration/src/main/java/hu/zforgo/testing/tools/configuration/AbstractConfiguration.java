package hu.zforgo.testing.tools.configuration;


import hu.zforgo.common.util.StringUtil;

import java.util.NoSuchElementException;

abstract class AbstractConfiguration implements Configuration {

	@FunctionalInterface
	private interface Callable<T> {
		T call();
	}

	private <T> T safe(Callable<T> c, T defaultValue) {
		try {
			return c.call();
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	public abstract Object get(String key);

	@Override
	public Object get(String key, Object defaultValue) {
		try {
			return get(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	@Override
	public boolean boolValue(String key) {
		Object o = get(key);
		return o != null && ((o instanceof String && ((String) o).trim().equals("1")) || Boolean.parseBoolean(o.toString().trim()));
	}

	@Override
	public boolean boolValue(String key, boolean defaultValue) {
		return safe(() -> boolValue(key), defaultValue);
	}

	@Override
	public Boolean getBoolean(String key) {
		return boolValue(key);
	}

	@Override
	public Boolean getBoolean(String key, Boolean defaultValue) {
		return safe(() -> getBoolean(key), defaultValue);
	}

	@Override
	public byte byteValue(String key) {
		return Byte.parseByte(get(key).toString().trim());
	}

	@Override
	public byte byteValue(String key, byte defaultValue) {
		return safe(() -> byteValue(key), defaultValue);
	}

	@Override
	public short shortValue(String key) {
		return Short.parseShort(get(key).toString().trim());
	}

	@Override
	public short shortValue(String key, short defaultValue) {
		return safe(() -> shortValue(key), defaultValue);
	}

	@Override
	public int intValue(String key) {
		return Integer.parseInt(get(key).toString().trim());
	}

	@Override
	public int intValue(String key, int defaultValue) {
		return safe(() -> intValue(key), defaultValue);
	}

	@Override
	public long longValue(String key) {
		return Long.parseLong(get(key).toString().trim());
	}

	@Override
	public long longValue(String key, long defaultValue) {
		return safe(() -> longValue(key), defaultValue);
	}

	@Override
	public float floatValue(String key) {
		return Float.parseFloat(get(key).toString().trim());
	}

	@Override
	public float floatValue(String key, float defaultValue) {
		return safe(() -> floatValue(key), defaultValue);
	}

	@Override
	public double doubleValue(String key) {
		return Double.parseDouble(get(key).toString().trim());
	}

	@Override
	public double doubleValue(String key, double defaultValue) {
		return safe(() -> doubleValue(key), defaultValue);
	}

	@Override
	public Byte getByte(String key) {
		return Byte.valueOf(getString(key));
	}

	@Override
	public Byte getByte(String key, Byte defaultValue) {
		return safe(() -> getByte(key), defaultValue);
	}

	@Override
	public Short getShort(String key) {
		return Short.valueOf(getString(key));
	}

	@Override
	public Short getShort(String key, Short defaultValue) {
		return safe(() -> getShort(key), defaultValue);
	}

	@Override
	public Integer getInteger(String key) {
		return Integer.valueOf(getString(key));
	}

	@Override
	public Integer getInteger(String key, Integer defaultValue) {
		return safe(() -> getInteger(key), defaultValue);
	}

	@Override
	public Long getLong(String key) {
		return Long.valueOf(getString(key));
	}

	@Override
	public Long getLong(String key, Long defaultValue) {
		return safe(() -> getLong(key), defaultValue);
	}

	@Override
	public Float getFloat(String key) {
		return Float.valueOf(getString(key));
	}

	@Override
	public Float getFloat(String key, Float defaultValue) {
		return safe(() -> getFloat(key), defaultValue);
	}

	@Override
	public Double getDouble(String key) {
		return Double.valueOf(getString(key));
	}

	@Override
	public Double getDouble(String key, Double defaultValue) {
		return safe(() -> getDouble(key), defaultValue);
	}

	@Override
	public String getString(String key) {
		return String.valueOf(get(key));
	}

	@Override
	public String getString(String key, String defaultValue) {
		return safe(() -> getString(key), defaultValue);
	}

	@Override
	public String[] getStringArray(String key) {
		return getStringArray(key, StringUtil.DEFAULT_DELIMITER);
	}

	@Override
	public String[] getStringArray(String key, char delimiter) {
		return getString(key).split(String.valueOf(delimiter));
	}

	@Override
	public String[] getStringArray(String key, String[] defaultValue) {
		return safe(() -> getStringArray(key), defaultValue);
	}

	@Override
	public String[] getStringArray(String key, char delimiter, String[] defaultValue) {
		return safe(() -> getStringArray(key, delimiter), defaultValue);
	}
}
