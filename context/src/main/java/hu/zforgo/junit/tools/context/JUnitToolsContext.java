package hu.zforgo.junit.tools.context;

import hu.zforgo.StringUtil;
import hu.zforgo.junit.tools.configuration.Configuration;
import hu.zforgo.junit.tools.configuration.ConfigurationFactory;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JUnitToolsContext {

	private static final Logger LOG = LoggerFactory.getLogger(JUnitToolsContext.class);
	private static volatile boolean inited;
	private static volatile JUnitToolsContext instance;
	private static volatile ClassLoader contextClassLoader;

	private JUnitToolsContext() {
	}

	protected static synchronized void createContext(Description description) throws ContextInitializationException {
		if (instance != null) {
			LOG.warn("JUnitTools context class ({}) has already created.", instance.getClass().getName());
			return;
		}
		LOG.info("Creating JUnitToolsContext...");
		JUnitToolsContext currentContext = new JUnitToolsContext();

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) {
			cl = JUnitToolsContext_work.class.getClassLoader();
			if (cl == null) {
				cl = ClassLoader.getSystemClassLoader();
			}
		}
		if (contextClassLoader == null) {
			contextClassLoader = cl;
		}

		try {
			Configuration contextConfig = ConfigurationFactory.load(StringUtil.isEmpty(System.getProperty(Defaults.CONFIG_FILENAME_VARIABLE)) ? Defaults.CONFIG_FILENAME : System.getProperty(Defaults.CONFIG_FILENAME_VARIABLE));

			currentContext.init(description);
		} catch (ContextInitializationException e) {
			LOG.error("An error occurred during initialize context", e);

		} catch (Exception e) {
			LOG.error("A fatal error occurred during initialize context", e);
			throw new ContextInitializationFailure(e);
		}
		LOG.info("JUnitToolsContext was successfully initialized.");

		if (instance == null) {
			instance = currentContext;
		}
	}

	public static JUnitToolsContext getInstance() {
		return instance;
	}

	protected void init(Description description) throws ContextInitializationException {
		inited = true;
	}

	protected synchronized void finalize(Result result) {
		inited = false;
	}
}
