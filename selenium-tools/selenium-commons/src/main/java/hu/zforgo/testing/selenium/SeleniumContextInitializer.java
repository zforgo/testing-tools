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

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SeleniumContextInitializer implements ContextInitializer {

	private static final String prefix_PROXY = "proxy.";
	private static final String prefix_DRIVER = "driver.";
	//TODO move to common place and change visibility to public
	private static final String prefix_DEFAULT = "DEFAULT.";
	//TODO move to common place and change visibility to public
	private static final String key_ENABLED = "enabled";
	private static final char DOT = '.';

	private static volatile Proxy defaultProxy;
	private static volatile boolean defaultProxyEnabled;

	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
//Init proxy
		Configuration proxies = contextConfig.submap(prefix_PROXY);
		Configuration defaultProxyConfig = proxies.submap(prefix_DEFAULT);

		defaultProxyEnabled = defaultProxyConfig.boolValue(key_ENABLED, true);
		defaultProxy = defaultProxyEnabled && !defaultProxyConfig.isEmpty() ? new Proxy(defaultProxyConfig.asMap()) : null;

		Map<DriverSetup, Proxy> proxyMap = Arrays.stream(DriverSetup.values())
				.map(d -> Tuple.of(d, buildProxy(proxies.submap(d.name() + DOT))))
				.filter(t -> Objects.nonNull(t._2))
				.collect(Collectors.toMap(t -> t._1, t -> t._2));

//init capabilities
		Configuration capabilities = contextConfig.submap(prefix_DRIVER);
		Configuration defaultCapability = capabilities.submap(prefix_DEFAULT);
		Map<DriverSetup, Configuration> capabilityMap = Arrays.stream(DriverSetup.values())
				.collect(
						Collectors.toMap(Function.identity(), d -> ConfigurationFactory.merge(capabilities.submap(d.name() + DOT), defaultCapability)));
	}

	private Proxy buildProxy(Configuration c) {
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

	@Override
	public void shutdown() throws Exception {

	}
}
