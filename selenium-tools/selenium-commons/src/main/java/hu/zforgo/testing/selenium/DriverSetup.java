package hu.zforgo.testing.selenium;

import hu.zforgo.common.util.CollectionUtil;
import hu.zforgo.common.util.StringUtil;
import hu.zforgo.testing.tools.configuration.Configuration;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

public enum DriverSetup {
	FIREFOX {
		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras) {
			DesiredCapabilities base = DesiredCapabilities.firefox();
			return addProxy(
					new DesiredCapabilities(base, new DesiredCapabilities(extras.asMap())), proxy);
		}

		@Override
		public WebDriver driver(DesiredCapabilities capabilities) {
			return new FirefoxDriver(capabilities);
		}
	},
	CHROME {
		private final String[] defaultSwitches = new String[]{"--no-default-browser-check"};
		private final Map<String, Object> defaultPrefs = new HashMap<>();
		{
			defaultPrefs.put("profile.password_manager_enabled", "false");
		}
		private final Map<String, Object> defaultOptions = new HashMap<>();
//		{
////			webdriver.chrome.driver system property
//			defaultOptions.put("binary", Option.of(System.getenv("CHROME_EXECUTABLE")).orElse(""));
//		}

		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras) {
			DesiredCapabilities base = DesiredCapabilities.chrome();
			DesiredCapabilities extraCaps = new DesiredCapabilities(extras.remains("switches").remains("prefs").remains("options").asMap());
			extraCaps.setCapability("chrome.switches",
					CollectionUtil.unionArraysAsSet(extras.getStringArray("switches", StringUtil.EMPTY_STRING_ARRAY), defaultSwitches));

			extraCaps.setCapability("chrome.prefs",
					CollectionUtil.unionMap(defaultPrefs, extras.submap("prefs.").asMap()));

			extraCaps.setCapability(ChromeOptions.CAPABILITY,
					CollectionUtil.unionMap(defaultOptions, extras.submap("options.").asMap()));
			return addProxy(new DesiredCapabilities(base, extraCaps), proxy);
		}

		@Override
		public WebDriver driver(DesiredCapabilities capabilities) {
			return new ChromeDriver(capabilities);
		}
	},
	IE {
		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras) {
			DesiredCapabilities base = DesiredCapabilities.internetExplorer();
			base.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			base.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			base.setCapability("requireWindowFocus", true);
			return addProxy(
					new DesiredCapabilities(base, new DesiredCapabilities(extras.asMap())), proxy);
		}

		@Override
		public WebDriver driver(DesiredCapabilities capabilities) {
			return new InternetExplorerDriver(capabilities);
		}
	},
	HTMLUNIT {
		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras) {
			DesiredCapabilities base = DesiredCapabilities.htmlUnitWithJs();
			return addProxy(
					new DesiredCapabilities(base, new DesiredCapabilities(extras.asMap())), proxy);
		}

		@Override
		public WebDriver driver(DesiredCapabilities capabilities) {
			return new HtmlUnitDriver(capabilities);
		}
	};

	public abstract DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras);

	public abstract WebDriver driver(DesiredCapabilities capabilities);

	private static DesiredCapabilities addProxy(DesiredCapabilities capabilities, Proxy proxy) {
		if (proxy != null) {
			capabilities.setCapability(PROXY, proxy);
		}
		return capabilities;
	}
}
