package hu.zforgo.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtil {

	@SafeVarargs
	public static <T> boolean isEmpty(T[]... input) {
		return input == null || input.length == 0;
	}

	public static boolean isEmpty(Collection input) {
		return input == null || input.isEmpty();
	}

	public static boolean isEmpty(Map input) {
		return input == null || input.isEmpty();
	}

	public static <T> BinaryOperator<T> mergeDuplicates(boolean override) {
		return (u, v) -> override ? v : u;
	}

	public static <T> BinaryOperator<T> preserveMerger() {
		return mergeDuplicates(false);
	}

	public static <T> BinaryOperator<T> abandonMerger() {
		return mergeDuplicates(true);
	}

	@SafeVarargs
	public static <K, V> Map<K, V> preservedUnionMap(Map<K, V>... maps) {
		return unionMap(true, maps);
	}

	@SafeVarargs
	public static <K, V> Map<K, V> unionMap(Map<K, V>... maps) {
		return unionMap(false, maps);
	}


	@SafeVarargs
	public static <K, V> Map<K, V> unionMap(boolean preserved, Map<K, V>... maps) {
		return Stream.of(maps)
				.map(Map::entrySet)
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, preserved ? preserveMerger() : abandonMerger()));
	}

	@SafeVarargs
	public static <T> List<T> unionArraysAsList(T[]... arrays) {
		return Stream.of(arrays)
				.map(Arrays::asList)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	@SafeVarargs
	public static <T> Set<T> unionArraysAsSet(T[]... arrays) {
		return Stream.of(arrays)
				.map(Arrays::asList)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

}
