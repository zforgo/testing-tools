package hu.zforgo.testing.selenium.support.pagefactory;

import javaslang.control.Try;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class PageFactory extends org.openqa.selenium.support.PageFactory {


	public static <T> T initElements(WebDriver driver, Class<T> page, Consumer<T> verifier) {
		T obj = initElements(driver, page, false);
		verifier.accept(obj);
		return obj;

	}

	public static <T> T initElements(WebDriver driver, Class<T> type) {
		return initElements(driver, type, true);
	}

	private static <T> T initElements(WebDriver driver, Class<T> type, boolean invokeVerifier) {
		T page = instantiatePage(type, driver);
		initElements(driver, page);
		if (invokeVerifier) {
			Optional<Method> method = Stream.of(type.getMethods()).filter(m -> m.isAnnotationPresent(Verifier.class)).findFirst();
			if (method.isPresent()) {
				Method m = method.get();
				try {
					switch (m.getParameterCount()) {
						case 0:
							m.invoke(page);
							break;
						case 1:
							m.invoke(page, driver);
							break;
						default:
							throw new IllegalArgumentException("Bad Verifier method: " + m.getName());
					}
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new WebDriverException("Cannot invoke @Verifier method: " + m.getName());
				}
			}

		}
		return page;

	}

	public static void initElements(WebDriver driver, Object page) {

//		TODO handle Pagepart cross inject (infinite loop)
		Class<?> klazz = page.getClass();
		while (klazz != Object.class) {
			Stream.of(klazz.getDeclaredFields())
					.filter(f -> f.isAnnotationPresent(Pagepart.class) && !f.getType().isPrimitive())
					.forEach(f -> {
						try {
							f.setAccessible(true);
							f.set(page, initElements(driver, f.getType()));
						} catch (IllegalAccessException e) {
							throw new RuntimeException(e);
						}
					});
			klazz = klazz.getSuperclass();
		}
		initElements(new DefaultFieldDecorator(new DefaultElementLocatorFactory(driver)), page);
	}


	private static <T> T instantiatePage(Constructor<T> c, Object... objects) {
		Objects.requireNonNull(c, "Constructor parameter should not be null");
		c.setAccessible(true);
		return Try.of(() -> c.newInstance(objects))
				.getOrElseThrow((Function<Throwable, RuntimeException>) RuntimeException::new);
	}

	private static <T> T instantiatePage(Class<T> type, WebDriver driver) {
		return Try.of(() -> instantiatePage(type.getDeclaredConstructor(WebDriver.class), driver))
				.getOrElseTry(() -> instantiatePage(type.getDeclaredConstructor()));
	}

}