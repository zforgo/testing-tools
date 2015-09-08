package hu.zforgo;

import java.lang.reflect.Field;
import java.util.Objects;

public class ClassUtil {

	private ClassUtil() {
	}

	public static Field findField(Class<?> cl, String fieldName) throws NoSuchFieldException {
		try {
			return cl.getDeclaredField(fieldName);
		} catch (SecurityException sex) {
			return null;
		} catch (NoSuchFieldException e) {
			if (Objects.equals(cl, Object.class)) {
				throw e;
			}
			return findField(cl.getSuperclass(), fieldName);
		}
	}

	public static <T> T fieldValue(Class<?> cl, String fieldName) throws NoSuchFieldException {
		Field f = findField(cl, fieldName);
		assert f != null;
		f.setAccessible(true);
		try {
			//noinspection unchecked
			return (T) f.get(fieldName);
		} catch (IllegalAccessException e) {
			throw new NoSuchFieldException(String.format("Couldn't get value for field %s.%s", cl, fieldName));
		}

	}
}