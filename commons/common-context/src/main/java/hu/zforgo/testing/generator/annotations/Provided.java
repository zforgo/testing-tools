package hu.zforgo.testing.generator.annotations;

import hu.zforgo.testing.generator.JSONDataGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Provided {
	Class<?> provider() default JSONDataGenerator.class;

	Param[] params() default {};

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.ANNOTATION_TYPE})
	@interface Param {
		String name();
		String value();
	}
}
