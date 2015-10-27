package hu.zforgo.common.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtil {

	public static boolean isEmpty(Collection input) {
		return input == null || input.isEmpty();
	}

	public static boolean isEmpty(Map input) {
		return input == null || input.isEmpty();
	}
}
