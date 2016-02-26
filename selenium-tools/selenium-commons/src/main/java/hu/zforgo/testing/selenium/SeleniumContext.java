package hu.zforgo.testing.selenium;

import hu.zforgo.common.util.CollectionUtil;
import hu.zforgo.testing.context.ContextInitializationException;
import hu.zforgo.testing.context.ContextInitializationFailure;
import hu.zforgo.testing.context.ContextInitializer;
import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testing.tools.configuration.Configuration;
import hu.zforgo.testing.tools.configuration.ConfigurationFactory;
import javaslang.Tuple;
import javaslang.Tuple2;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hu.zforgo.common.util.ConfigConstants.DOT;
import static hu.zforgo.common.util.ConfigConstants.key_ENABLED;
import static hu.zforgo.common.util.ConfigConstants.prefix_DEFAULT;

public final class SeleniumContext implements ContextInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(SeleniumContext.class);
	private static final String prefix_PROXY = "selenium.proxy.";
	private static final String prefix_PREFLOG = "selenium.logging.";
	private static final String prefix_DRIVER = "selenium.driver.";
	private static final String[] logtypes = new String[]{LogType.BROWSER, LogType.CLIENT, LogType.DRIVER, LogType.PERFORMANCE, LogType.PROFILER, LogType.SERVER};

	private static volatile SeleniumContext instance;

	private static volatile boolean inited;

	private static volatile Map<DriverSetup, Tuple2<Proxy, Configuration>> driverConfig;
	private static volatile LoggingPreferences defaultPreflog;

	private static volatile URL remoteHub;
	private static volatile Mode mode = Mode.LOCAL;

	private enum Mode {
		LOCAL,
		REMOTE
	}

	public static Function<Configuration, LoggingPreferences> buildLoggingPreferences = configuration -> {
		LoggingPreferences preflog = new LoggingPreferences();
		Stream.of(logtypes).forEach(t -> {
					preflog.enable(t, Level.parse(configuration.getString(t, configuration.getString(t.toUpperCase(), Level.OFF.getName()))));
				}
		);
		return preflog;
	};

	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
		if (inited) {
			throw new ContextInitializationException("SeleniumContext has been already created.");
		}

		LOG.info("Creating TestingToolsContext...");
		//Configure proxies
		Configuration proxies = contextConfig.submap(prefix_PROXY);
		Configuration defaultProxyConfig = proxies.submap(prefix_DEFAULT);

		boolean defaultProxyEnabled = defaultProxyConfig.boolValue(key_ENABLED, true);
		Proxy defaultProxy = defaultProxyEnabled && !defaultProxyConfig.isEmpty() ? new Proxy(defaultProxyConfig.asMap()) : null;

		//Configure LoggingPreferences
		try {
			defaultPreflog = buildLoggingPreferences.apply(contextConfig.submap(prefix_PREFLOG));
		} catch (IllegalArgumentException e) {
			throw new ContextInitializationFailure("Unable to configure logging preferences", e);
		}


		//Configure HUB
		mode = contextConfig.getEnum("selenium.mode", Mode.LOCAL);
		if (Mode.REMOTE == mode) {
			try {
				remoteHub = new URL(contextConfig.getString("selenium.hub"));
			} catch (MalformedURLException | NoSuchElementException e) {
				throw new ContextInitializationFailure("Unable to configure Selenium HUB", e);
			}
		}
		Configuration capabilities = contextConfig.submap(prefix_DRIVER);
		Configuration defaultCapability = capabilities.submap(prefix_DEFAULT);

		driverConfig = Stream.of(DriverSetup.values())
				.map(
						d -> Tuple.of(
								d,
								buildProxy(proxies.submap(d.name() + DOT), defaultProxy, defaultProxyEnabled),
								ConfigurationFactory.merge(capabilities.submap(d.name() + DOT), defaultCapability)
						))
				.collect(Collectors.toMap(t -> t._1, t -> Tuple.of(t._2, t._3)));

		inited = true;
		if (instance == null) {
			instance = this;
		}
	}

	public static SeleniumContext getInstance() {
//		FIXME throw ex if instance is null or it isn't inited
		return instance;
	}

	public WebDriver driver(DriverSetup ds) {
		Tuple2<Proxy, Configuration> config = driverConfig.get(ds);
		DesiredCapabilities cap = ds.buildCapabilities(config._1, config._2, defaultPreflog);
		return mode == Mode.REMOTE ? new RemoteWebDriver(remoteHub, cap) : ds.driver(cap);
	}

	@Override
	public void shutdown() throws Exception {

	}


	private static Proxy buildProxy(Configuration c, Proxy defaultProxy, boolean defaultProxyEnabled) {
		if (!c.boolValue(key_ENABLED, defaultProxyEnabled)) {
			return null;
		}
		Map<String, Object> c2 = c.remains(key_ENABLED).asMap();
		//Hack for Proxy constructor
		if (c2.containsKey("autodetect")) {
			c2.put("autodetect", Boolean.parseBoolean(String.valueOf(c2.get("autodetect"))));
		}
		return CollectionUtil.isEmpty(c2) ? defaultProxy : new Proxy(c2);
	}

}
