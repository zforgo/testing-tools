package hu.zforgo.junit.tools.context;

import hu.zforgo.junit.tools.context.annotations.AlwaysRun;
import hu.zforgo.junit.tools.context.annotations.Groups;
import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupRule extends TestWatcher {

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				List<Throwable> errors = new ArrayList<>();

				try {
					Assume.assumeTrue("TODO method skipped", assume(description));
					base.evaluate();

				} catch (AssumptionViolatedException e) {
					errors.add(e);
					skipped(e, description);
				} catch (Throwable e) {
					errors.add(e);
					failed(e, description);
				} finally {
					finished(description);
				}

				MultipleFailureException.assertEmpty(errors);

			}
		};
	}

	private boolean assume(final Description desc) {
		final String groupsProperty = System.getProperty("junit.groups", "").trim();
		final Class<?> testClass = desc.getTestClass();
		if (null != desc.getAnnotation(AlwaysRun.class) || null != testClass.getAnnotation(AlwaysRun.class) || groupsProperty.length() == 0) {
			return true;
		}
		Groups methodGroups = desc.getAnnotation(Groups.class);
		Groups classGroups = testClass.getAnnotation(Groups.class);
		if (methodGroups == null && classGroups == null) {
			return false;
		}

		List<String> enabledGroups = Stream.of(groupsProperty.split(",")).map(String::toLowerCase).collect(Collectors.toList());

		return Stream.concat(
				methodGroups == null ? Stream.empty() : Stream.of(methodGroups.value()),
				classGroups == null ? Stream.empty() : Stream.of(classGroups.value())
		).map(String::toLowerCase).map(String::trim)
				.filter(enabledGroups::contains)
				.findFirst().isPresent();
	}
}
