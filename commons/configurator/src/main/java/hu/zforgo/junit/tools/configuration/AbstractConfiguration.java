package hu.zforgo.junit.tools.configuration;

import hu.zforgo.StringUtil;

import java.util.NoSuchElementException;

abstract class AbstractConfiguration implements Configuration {

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
		try {
			return boolValue(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	@Override
	public byte byteValue(String key) {
		return Byte.parseByte(get(key).toString().trim());
	}

	@Override
	public byte byteValue(String key, byte defaultValue) {
		try {
			return byteValue(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	@Override
	public short shortValue(String key) {
		return Short.parseShort(get(key).toString().trim());
	}

	@Override
	public short shortValue(String key, short defaultValue) {
		try {
			return shortValue(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	@Override
	public int intValue(String key) {
		return Integer.parseInt(get(key).toString().trim());
	}

	@Override
	public int intValue(String key, int defaultValue) {
		try {
			return intValue(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	@Override
	public long longValue(String key) {
		return Long.parseLong(get(key).toString().trim());
	}

	@Override
	public long longValue(String key, long defaultValue) {
		try {
			return longValue(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	@Override
	public float floatValue(String key) {
		return Float.parseFloat(get(key).toString().trim());
	}

	@Override
	public float floatValue(String key, float defaultValue) {
		try {
			return floatValue(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	@Override
	public double doubleValue(String key) {
		return Double.parseDouble(get(key).toString().trim());
	}

	@Override
	public double doubleValue(String key, double defaultValue) {
		try {
			return doubleValue(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	@Override
	public String getString(String key) {
		return String.valueOf(get(key));
	}

	@Override
	public String getString(String key, String defaultValue) {
		try {
			return getString(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
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
		try {
			return getStringArray(key);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}

	@Override
	public String[] getStringArray(String key, char delimiter, String[] defaultValue) {
		try {
			return getStringArray(key, delimiter);
		} catch (NoSuchElementException e) {
			return defaultValue;
		}
	}
}
