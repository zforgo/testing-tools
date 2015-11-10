package hu.zforgo.testing.selenium;

import hu.zforgo.common.util.CollectionUtil;
import hu.zforgo.testing.context.ContextInitializationException;
import hu.zforgo.testing.context.ContextInitializationFailure;
import hu.zforgo.testing.context.ContextInitializer;
import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testing.tools.configuration.Configuration;
import org.openqa.selenium.Proxy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SeleniumContextInitializer implements ContextInitializer {

	private static final String prefix_PROXY = "proxy.";
	private static final String prefix_DRIVER = "driver.";
	//TODO move to common place and change visibility to public
	private static final String prefix_DEFAULT = "DEFAULT.";
	//TODO move to common place and change visibility to public
	private static final String key_ENABLED = "enabled";
	private static final char DOT = '.';


	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
//Init proxy
		Configuration proxies = contextConfig.submap(prefix_PROXY);
		Configuration defaultProxyConfig = proxies.submap(prefix_DEFAULT);

		final boolean defaultProxyEnabled = defaultProxyConfig.boolValue(key_ENABLED, true);

		final Proxy defaultProxy = defaultProxyEnabled && !defaultProxyConfig.isEmpty() ? new Proxy(defaultProxyConfig.asMap()) : null;

		Map<DriverSetup, Proxy> proxyMap = new HashMap<>();
		Arrays.stream(DriverSetup.values())
				.forEach(d -> proxyMap.put(d, buildProxy(proxies.submap(d.name() + DOT), defaultProxy, defaultProxyEnabled)));
		System.out.println(proxyMap);

//init capabilities
//		Configuration capabilities = contextConfig.submap(prefix_PROXY);
//		Configuration defaultCapability = capabilities.submap(prefix_DEFAULT);
//		DesiredCapabilities desiredCapabilities = !defaultCapability.isEmpty() ? new DesiredCapabilities()
//		Map<DriverSetup, DesiredCapabilities> capabilitiesMap = new HashMap<>();
	}

//	private DesiredCapabilities buildCapabilities(Configuration c) {
//
//	}
	private Proxy buildProxy(Configuration c, Proxy defaultProxy, boolean defaultEnabled) {
		if (!c.boolValue(key_ENABLED, defaultEnabled)) {
			return null;
		}
		Map<String, Object> c2 = c.remains(key_ENABLED).asMap();
		//Hack for Proxy constructor
		if (c2.containsKey("autodetect")) {
			c2.put("autodetect", Boolean.parseBoolean(String.valueOf(c2.get("autodetect"))));
		}
		return CollectionUtil.isEmpty(c2) ? defaultProxy : new Proxy(c2);
	}

	@Override
	public void shutdown() throws Exception {

	}
}
