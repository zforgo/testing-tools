package hu.zforgo.common.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

	public static <T> T loadService(Class<T> serviceClass) {
		Iterator<T> providerIterator = ServiceLoader.load(serviceClass).iterator();
		return providerIterator.hasNext() ? providerIterator.next() : null;
	}

	public static <T> T loadService(Class<T> serviceClass, ClassLoader cl) {
		Iterator<T> providerIterator = ServiceLoader.load(serviceClass, cl).iterator();
		return providerIterator.hasNext() ? providerIterator.next() : null;
	}

	public static <T> T loadService(Class<T> serviceClass, DefaultServiceCreator<T> creator) {
		Iterator<T> providerIterator = ServiceLoader.load(serviceClass).iterator();
		return providerIterator.hasNext() ? providerIterator.next() : creator.create();
	}

	public static <T> T loadService(Class<T> serviceClass, ClassLoader cl, DefaultServiceCreator<T> creator) {
		Iterator<T> providerIterator = ServiceLoader.load(serviceClass, cl).iterator();
		return providerIterator.hasNext() ? providerIterator.next() : creator.create();
	}

	public static <T> List<T> loadServices(Class<T> serviceClass) {
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(ServiceLoader.load(serviceClass).iterator(), Spliterator.ORDERED), false)
				.collect(Collectors.toList());
	}
}