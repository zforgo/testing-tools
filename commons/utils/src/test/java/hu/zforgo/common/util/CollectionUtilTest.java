package hu.zforgo.common.util;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionUtilTest {

	@Test
	public void preserveTest() {
		Map<String, Object> foo = new HashMap<>();
		foo.put("one", "foo");
		foo.put("two", "bar");
		foo.put("three", "baz");

		Map<String, Object> bar = new HashMap<>();
		bar.put("one_bar", "foo");
		bar.put("two", "baragain");
		{
			Map<String, Object> result = CollectionUtil.preservedUnionMap(foo, bar);
			assertThat(result).isNotEmpty().hasSize(4);
			assertThat(result.get("two")).isEqualTo("bar");
		}
		{
			Map<String, Object> result = CollectionUtil.preservedUnionMap(foo, bar, Collections.emptyMap(), Collections.singletonMap("two", "preserved"));
			assertThat(result).isNotEmpty().hasSize(4);
			assertThat(result.get("two")).isEqualTo("bar");
		}
	}

	@Test
	public void abandomTest() {
		Map<String, Object> foo = new HashMap<>();
		foo.put("one", "foo");
		foo.put("two", "bar");
		foo.put("three", "baz");

		Map<String, Object> bar = new HashMap<>();
		bar.put("one_bar", "foo");
		bar.put("two", "baragain");
		{
			Map<String, Object> result = CollectionUtil.unionMap(foo, bar);
			assertThat(result).isNotEmpty().hasSize(4);
			assertThat(result.get("two")).isEqualTo("baragain");
			assertThat(result.get("three")).isEqualTo("baz");
		}
		{
			Map<String, Object> result = CollectionUtil.unionMap(foo, bar, Collections.emptyMap(), Collections.singletonMap("two","abandoned"));
			assertThat(result).isNotEmpty().hasSize(4);
			assertThat(result.get("two")).isEqualTo("abandoned");
			assertThat(result.get("three")).isEqualTo("baz");
		}
	}
}
