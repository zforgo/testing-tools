import hu.zforgo.testing.selenium.support.pagefactory.Pagepart;
import javaslang.control.Try;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class Injector {


	public static <T> T initElements(WebDriver driver, Class<T> type) {
		T page = createObj(type, driver);
		initElements(driver, page);
		return page;
	}


	public static <T> void initElements(WebDriver driver, T page) {

//		TODO handle Pagepart cross inject (infinite loop)
		Class<?> klazz = page.getClass();
		while (klazz != Object.class) {
			Stream.of(klazz.getDeclaredFields())
					.filter(f -> f.isAnnotationPresent(Pagepart.class) && !f.getType().isPrimitive())
					.forEach(f -> {
						try {
							f.setAccessible(true);
							f.set(page, initElements(driver, f.getType()));
							System.out.println(f.toGenericString() + " has been set");
						} catch (IllegalAccessException e) {
							throw new RuntimeException(e);
						}
					});
			klazz = klazz.getSuperclass();
		}
		initElements(new DefaultFieldDecorator(new DefaultElementLocatorFactory(driver)), page);
	}

	private static void initElements(FieldDecorator decorator, Object page) {
		Class<?> proxyIn = page.getClass();
		while (proxyIn != Object.class) {
			proxyFields(decorator, page, proxyIn);
			proxyIn = proxyIn.getSuperclass();
		}
	}

	private static void proxyFields(FieldDecorator decorator, Object page, Class<?> proxyIn) {
		Field[] fields = proxyIn.getDeclaredFields();
		for (Field field : fields) {
			Object value = decorator.decorate(page.getClass().getClassLoader(), field);
			if (value != null) {
				try {
					field.setAccessible(true);
					field.set(page, value);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private static <T> T createObject(Constructor<T> c, Object... objects) {
		Objects.requireNonNull(c, "Constructor is null");
		c.setAccessible(true);
		return Try.of(() -> c.newInstance(objects))
				.getOrElseThrow((Function<Throwable, RuntimeException>) RuntimeException::new);
	}

	private static <T> T createObj(Class<T> type, WebDriver driver) {
		return Try.of(() -> createObject(type.getDeclaredConstructor(WebDriver.class), driver))
				.getOrElseTry(() -> createObject(type.getDeclaredConstructor()));
	}

}
