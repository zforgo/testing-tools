package hu.zforgo.junit.tools.configuration;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerRule extends TestWatcher {

	private Logger LOG;

	private String type;

	public LoggerRule(String type) {
		this.type = type;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		LOG = LoggerFactory.getLogger(description.getTestClass());
		return super.apply(base, description);
	}

	@Override
	protected void starting(Description d) {
		LOG.info(String.format("Starting test [%s] by type [%s]", d.getMethodName(), type));
	}

	@Override
	protected void succeeded(Description description) {
		LOG.info(String.format("[%s] by type [%s] is finished successfully", description.getMethodName(), type));
	}

	@Override
	protected void failed(Throwable e, Description description) {
		LOG.error(String.format("[%s] by type [%s] is failed. Message is: %s", description.getMethodName(), type, e.getMessage()));
	}
}
