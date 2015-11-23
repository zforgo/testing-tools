package hu.zforgo.testng.tools.context;

import hu.zforgo.testing.generator.annotations.Provided;
import hu.zforgo.testing.generator.annotations.Provided.Param;
import hu.zforgo.testing.generator.annotations.Provider;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Stream;
import org.testng.annotations.DataProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;

;

public class DataGenerator {
	@Deprecated
	private static final Object[][] RESULT = new Object[0][0];

	private enum Compatible {
		FULL,
		PARTIAL,
		NONE
	}

	@DataProvider
	public static Object[][] generate(Method method) {
		//Each parameter and their annotations (all of them)
		Stream.of(method.getGenericParameterTypes()).zip(Arrays.asList(method.getParameterAnnotations()))
				.map(t -> generateValue(t._1, t._2))
				.forEach(System.out::println);
		//TODO remove remove forEach
		//TODO return valid result
		return RESULT;
	}

	//Generate data by annotation
	private static <T> T generateValue(Type t, Annotation[] annotations /*all annotation's on test method parameter*/) {
		//There isn't any Provided annotation on parameter
		Annotation pro = Stream.of(annotations).findFirst(a -> a.annotationType().isAnnotation() && a.annotationType().isAssignableFrom(Provided.class)).orElse(null);
		//Provided annotation doesn't exists on the given parameter
		if (pro == null) {
			return null;
		}
		Provided ann = ((Provided) pro);
		//TODO invoke method and return valid result
		findCompatibleMethod(ann.provider(), ann.params(), t);
		return null;
	}

	private static void findCompatibleMethod(Class<?> generator, Param[] params, Type resultType) {
		Stream.of(generator.getDeclaredMethods())
				.filter(m -> m.isAnnotationPresent(Provider.class) && Modifier.isStatic(m.getModifiers()))
				.map(m -> DataGenerator.computeCompatibility(m, params, resultType))
				.filter(t -> t._2 != Compatible.NONE)
				.forEach(System.out::println);
//TODO remove forEach and return valid result
	}

	private static Tuple2<Method, Compatible> computeCompatibility(Method m, Param[] params, Type resultType) {
		if (!isCompatibleType(resultType, m.getGenericReturnType())) {
			return Tuple.of(m, Compatible.NONE);
		};

		Type[] paramTypes = m.getGenericParameterTypes();
		//TODO compute!
		return Tuple.of(m, Compatible.FULL);
	}

	private static boolean isCompatibleType(Type a, Type b) {
		return true;
	}

	private CharSequence valami(String a) {
		return a;
	}
}
