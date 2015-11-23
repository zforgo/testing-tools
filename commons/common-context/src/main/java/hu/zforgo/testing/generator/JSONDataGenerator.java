package hu.zforgo.testing.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.zforgo.testing.generator.annotations.Provider;

import java.util.List;
import java.util.Set;

public class JSONDataGenerator {
	private static final ThreadLocal<ObjectMapper> mapper = new ThreadLocal<ObjectMapper>() {
		@Override
		protected ObjectMapper initialValue() {
			return new ObjectMapper();
		}
	};

	@Provider
	public static String load() {
		//TODO finish
		return null;
	}

	@Provider
	public static Set<String> generate() {
		//TODO finish
		return null;
	}

	@Provider
	public static Object generate(String file) {
		return null;
	}

	@Provider
	public static Object generate(String file, List<Integer> list) {
		return null;
	}
}
