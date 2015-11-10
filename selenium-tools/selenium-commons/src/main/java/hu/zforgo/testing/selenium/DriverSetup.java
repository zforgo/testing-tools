package hu.zforgo.testing.selenium;

import hu.zforgo.common.util.CollectionUtil;
import hu.zforgo.common.util.StringUtil;
import hu.zforgo.testing.tools.configuration.Configuration;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

public enum DriverSetup {
//TODO more configuration options
	FIREFOX {
		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras) {
			DesiredCapabilities base = DesiredCapabilities.firefox();
			return addProxy(
					new DesiredCapabilities(base, new DesiredCapabilities(extras.asMap()))
					, proxy);
		}
	},
	CHROME {
		private final String[] defaultSwitches = new String[]{"--no-default-browser-check"};
		private Map<String, Object> defaultPrefs = new HashMap<>();
		{
			defaultPrefs.put("profile.password_manager_enabled", "false");
		}

		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras) {
			DesiredCapabilities base = DesiredCapabilities.chrome();

			DesiredCapabilities extraCaps = new DesiredCapabilities(extras.remains("switches").remains("prefs").asMap());
			extraCaps.setCapability("chrome.switches",
					CollectionUtil.unionArraysAsSet(extras.getStringArray("switches", StringUtil.EMPTY_STRING_ARRAY), defaultSwitches));

			extraCaps.setCapability("chrome.prefs",
					CollectionUtil.unionMap(defaultPrefs, extras.submap("prefs.").asMap()));
			return addProxy(new DesiredCapabilities(base, extraCaps), proxy);
		}


	},
	//TODO more configuration options
	HTMLUNIT {
		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras) {
			DesiredCapabilities capabilities = DesiredCapabilities.htmlUnitWithJs();
			return capabilities;
		}
	};

	public abstract DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras);

	private static DesiredCapabilities addProxy(DesiredCapabilities capabilities, Proxy proxy) {
		if (proxy != null) {
			capabilities.setCapability(PROXY, proxy);
		}
		return capabilities;
	}
}
