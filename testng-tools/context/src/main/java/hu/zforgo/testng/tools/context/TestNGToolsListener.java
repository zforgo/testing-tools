package hu.zforgo.testng.tools.context;

import hu.zforgo.common.util.StringUtil;
import hu.zforgo.testing.generator.annotations.Provided;
import javaslang.collection.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IAnnotationTransformer;
import org.testng.IExecutionListener;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;


public class TestNGToolsListener implements IExecutionListener, IAnnotationTransformer {
	private static final Logger LOG = LoggerFactory.getLogger(TestNGToolsListener.class);

	@Override
	public void onExecutionStart() {
		TestNGToolsContext.createContext();
	}

	@Override
	public void onExecutionFinish() {

	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		//Extract @Provided annotations
		boolean found = Stream.of(testMethod.getParameterAnnotations())
				.flatMap(Stream::of)
				.findFirst(s -> s.annotationType().isAssignableFrom(Provided.class))
				.isDefined();

		if (found) {
			//DataProvider has been set explicitly
			if (StringUtil.isNotEmpty(annotation.getDataProvider()) || Objects.nonNull(annotation.getDataProviderClass())) {
				LOG.warn("DataProvider has been set expicitly. @Provided annotation is ignored.");
				return;
			}
			annotation.setDataProviderClass(DataGenerator.class);
			annotation.setDataProvider("generate");
		}
	}
}
