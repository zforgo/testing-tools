package hu.zforgo.junit.tools.context;

import hu.zforgo.StringUtil;
import hu.zforgo.junit.tools.configuration.Configuration;
import hu.zforgo.junit.tools.configuration.ConfigurationFactory;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;


public class JUnitToolsContext {

	private static final Logger LOG = LoggerFactory.getLogger(JUnitToolsContext.class);
	private static volatile boolean inited;
	private static volatile JUnitToolsContext instance;
	private static volatile ClassLoader contextClassLoader;
	private static volatile Configuration contextConfig;
	private static volatile Set<Path> configLookupFolders;

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
			cl = JUnitToolsContext.class.getClassLoader();
			if (cl == null) {
				cl = ClassLoader.getSystemClassLoader();
			}
		}
		if (contextClassLoader == null) {
			contextClassLoader = cl;
		}

		try {
			{
				Configuration tmp = ConfigurationFactory.load(StringUtil.isEmpty(System.getProperty(Defaults.CONFIG_FILENAME_VARIABLE)) ? Defaults.CONFIG_FILENAME : System.getProperty(Defaults.CONFIG_FILENAME_VARIABLE));
				//TODO check tmp must not be empty
				if (contextConfig == null) {
					contextConfig = tmp;
				}
			}
			{
				Set<Path> tmp = prepareConfigPaths();
				if (configLookupFolders == null) {
					configLookupFolders = tmp;
				}
			}
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

	private static Set<Path> prepareConfigPaths() {
		final Path configRoot = Paths.get(contextConfig.getString(Defaults.LOOKUP_ROOT, contextConfig.getString(Defaults.BASEDIR, contextConfig.getString(Defaults.USER_DIR))));
		Iterator<ConfigurationPathProvider> providerIterator = ServiceLoader.load(ConfigurationPathProvider.class).iterator();
		if (providerIterator.hasNext()) {
			return providerIterator.next().getPaths(configRoot, contextConfig);
		}
		return Collections.singleton(configRoot);
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
