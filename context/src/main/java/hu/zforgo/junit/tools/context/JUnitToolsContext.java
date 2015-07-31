package hu.zforgo.junit.tools.context;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitToolsContext {

	private static final Logger LOG = LoggerFactory.getLogger(JUnitToolsContext.class);
	private static volatile boolean inited;
	private static volatile JUnitToolsContext instance;

	private JUnitToolsContext() {
	}

	protected static synchronized void createContext(Description description) {
		if (instance != null) {
			LOG.warn("JUnitTools context class ({}) has already created.", instance.getClass().getName());
			return;
		}
		LOG.info("Creating JUnitToolsContext...");
		JUnitToolsContext currentContext = new JUnitToolsContext();

		currentContext.init(description);
		LOG.info("JUnitToolsContext was successfully initialized.");

		if (instance == null) {
			instance = currentContext;
		}
	}

	public static JUnitToolsContext getInstance() {
		return instance;
	}

	protected void init(Description description) {
		inited = true;
	}

	protected synchronized void finalize(Result result) {
		inited = false;
	}
}
