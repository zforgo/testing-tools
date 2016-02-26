package hu.zforgo;

import hu.zforgo.common.util.ClassUtil;
import hu.zforgo.testing.selenium.DriverSetup;
import hu.zforgo.testing.selenium.SeleniumContext;
import hu.zforgo.testing.tools.configuration.Configuration;
import org.junit.Test;

import java.util.Map;

public class ConfigurationTest {

//	@Test
	public void chromeConfiguration() throws NoSuchFieldException {
//		SeleniumContext.getInstance();
		Map<DriverSetup, Configuration> driverConfig = ClassUtil.fieldValue(SeleniumContext.class, "driverConfig");
		System.out.println(driverConfig);
//		private static volatile Map<DriverSetup, Proxy> configuredProxies;
//		private static volatile Map<DriverSetup, Configuration> additionalCapabilities;
	}
}
