package hu.zforgo.testing.selenium;

import hu.zforgo.common.util.CollectionUtil;
import hu.zforgo.testing.context.ContextInitializationException;
import hu.zforgo.testing.context.ContextInitializationFailure;
import hu.zforgo.testing.context.ContextInitializer;
import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testing.tools.configuration.Configuration;
import hu.zforgo.testing.tools.configuration.ConfigurationFactory;
import javaslang.Tuple;
import org.openqa.selenium.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static hu.zforgo.common.util.ConfigConstants.DOT;
import static hu.zforgo.common.util.ConfigConstants.key_ENABLED;
import static hu.zforgo.common.util.ConfigConstants.prefix_DEFAULT;

public final class SeleniumContext implements ContextInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(SeleniumContext.class);
	private static final String prefix_PROXY = "proxy.";
	private static final String prefix_DRIVER = "driver.";

	private static volatile SeleniumContext instance;

	private static volatile boolean inited;

	private static volatile Map<DriverSetup, Proxy> configuredProxies;
	private static volatile Map<DriverSetup, Configuration> additionalCapabilities;

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

		if (CollectionUtil.isEmpty(configuredProxies)) {
			LOG.debug("Configuring Selenium proxies...");
			configuredProxies = Collections.unmodifiableMap(Arrays.stream(DriverSetup.values())
					.map(d -> Tuple.of(d, buildProxy(proxies.submap(d.name() + DOT), defaultProxy, defaultProxyEnabled)))
					.filter(t -> Objects.nonNull(t._2))
					.collect(Collectors.toMap(t -> t._1, t -> t._2)));
		}

		//Configure additional Capabilities
		if (CollectionUtil.isEmpty(additionalCapabilities)) {
			LOG.debug("Configuring additional Selenium Capabilities...");
			Configuration capabilities = contextConfig.submap(prefix_DRIVER);
			Configuration defaultCapability = capabilities.submap(prefix_DEFAULT);
			additionalCapabilities = Collections.unmodifiableMap(
					Arrays.stream(DriverSetup.values())
							.collect(
									Collectors.toMap(Function.identity(), d -> ConfigurationFactory.merge(capabilities.submap(d.name() + DOT), defaultCapability)))
			);
		}
		inited = true;
		if (instance == null) {
			instance = this;
		}
	}

	public static SeleniumContext getInstance() {
		return instance;
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
