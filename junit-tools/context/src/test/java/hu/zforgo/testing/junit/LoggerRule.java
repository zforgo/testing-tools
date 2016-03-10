package hu.zforgo.testing.junit;

import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerRule extends TestWatcher {

	private Logger LOG;

	@Override
	public Statement apply(Statement base, Description description) {
		LOG = LoggerFactory.getLogger(description.getTestClass());
		return super.apply(base, description);
	}

	@Override
	protected void starting(Description d) {
		LOG.info(String.format("Evaluating test [%s]", d.getMethodName()));
	}

	@Override
	protected void succeeded(Description description) {
		LOG.info(String.format("[%s] is finished successfully", description.getMethodName()));
	}

	@Override
	protected void failed(Throwable e, Description description) {
		LOG.error(String.format("[%s] is failed. Message is: %s", description.getMethodName(), e.getMessage()));
	}

	@Override
	protected void skipped(AssumptionViolatedException e, Description description) {
		LOG.info(String.format("[%s] is skipped.", description.getMethodName()));
	}
}
