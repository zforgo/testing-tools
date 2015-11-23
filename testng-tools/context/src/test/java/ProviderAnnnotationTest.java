import hu.zforgo.testing.generator.annotations.Provider;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.List;
import javaslang.collection.Stream;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ProviderAnnnotationTest {

	private enum Compatible {
		FULL,
		PARTIAL,
		NONE
	}

	@Test
	public void test() {
		Method method = Stream.of(ParameterTest.class.getDeclaredMethods()).findFirst(m-> m.getName().equals("test")).get();
		List<Tuple2<Type,Annotation[]>> foo = Stream.of(method.getGenericParameterTypes()).zip(Arrays.asList(method.getParameterAnnotations())).toList()
//				.map(t->generateValue(t._1, t._2))
//				.forEach(System.out::println);
		;

		Stream.of(TestProvider.class.getDeclaredMethods())
				.filter(m-> m.isAnnotationPresent(Provider.class) && Modifier.isStatic(m.getModifiers()))
				.map(this::computeCompatibility)
				.filter(t-> t._2 != Compatible.NONE)
				.forEach(System.out::println);
		System.out.println("valami");
	}

	public Tuple2<Method, Compatible> computeCompatibility(Method m) {
		return Tuple.of(m, Compatible.FULL);
	}
}
