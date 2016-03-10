package hu.zforgo.testing.selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import hu.zforgo.common.util.ClassUtil;
import hu.zforgo.common.util.StringUtil;
import hu.zforgo.testing.configuration.Configuration;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.openqa.selenium.remote.CapabilityType.PROXY;
import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

public enum DriverSetup {
	FIREFOX {
		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras, LoggingPreferences logs) {
			DesiredCapabilities base = DesiredCapabilities.firefox();
			FirefoxProfile firefoxProfile = new FirefoxProfile();

			Stream.of(extras.getStringArray("extensions", PATH_SEPARATOR, StringUtil.EMPTY_STRING_ARRAY))
					.map(File::new).forEachOrdered((extensionToInstall) -> {
				try {
					firefoxProfile.addExtension(extensionToInstall);
				} catch (IOException e) {
					throw new WebDriverException("Unable to install extension: " + extensionToInstall, e);
				}
			});

			Stream.of(extras.getStringArray("preferences", NEW_LINE, StringUtil.EMPTY_STRING_ARRAY))
					.map(s -> s.split("=", 2))
					.filter(a -> a.length == 2)
					.forEachOrdered(e ->
							firefoxProfile.setPreference(e[0].trim(), e[1].trim())
					);

			String binaryPath = extras.getString(BINARY, System.getenv(name() + EXECUTABLE_SUFFIX));
			FirefoxBinary binary = StringUtil.isWhite(binaryPath) ? null : new FirefoxBinary(new File(binaryPath));
			base.setCapability(FirefoxDriver.BINARY, binary);

			String page;
			if ((page = extras.getString(STARTPAGE, null)) != null) {
				firefoxProfile.setPreference("browser.startup.homepage", page);
			}
			base.setCapability(FirefoxDriver.PROFILE, firefoxProfile);


			return configureLogging(addProxy(base, proxy), logs, extras);
		}

		@Override
		public WebDriver driver(DesiredCapabilities capabilities) {
			return new FirefoxDriver(capabilities);
		}
	},

	CHROME {
		//		all available switches at: http://peter.sh/experiments/chromium-command-line-switches/
		private final String[] defaultArguments = new String[]{"--no-default-browser-check"};

		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras, LoggingPreferences logs) {
			DesiredCapabilities base = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();

			String binary = extras.getString(BINARY, System.getenv(name() + EXECUTABLE_SUFFIX));
			if (StringUtil.isNotEmpty(binary)) {
				options.setBinary(binary);
			}

			List<String> arguments = Stream.of(extras.getStringArray("arguments", NEW_LINE, defaultArguments))
					.map(String::trim)
					.collect(Collectors.toList());

			Map<String, Object> prefs = new HashMap<>();

			String page;
			if ((page = extras.getString(STARTPAGE, null)) != null) {
//				TODO log warn if user-data-dir isn't set explicitly
				arguments.add(arguments.stream().filter("user-data-dir"::contains).findFirst().orElse("user-data-dir=."));
				prefs.put("session.restore_on_startup", 4);
				prefs.put("session.startup_urls", Collections.singletonList(page));
			}

			Stream.of(extras.getStringArray("preferences", NEW_LINE, StringUtil.EMPTY_STRING_ARRAY))
					.map(s -> s.split("=", 2))
					.filter(a -> a.length == 2)
					.forEachOrdered(e ->
							prefs.put(e[0].trim(), e[1].trim())
					);

			options.setExperimentalOption("prefs", prefs);

			options.addArguments(arguments);

			options.addExtensions(
					Stream.of(extras.getStringArray("extensions", PATH_SEPARATOR, StringUtil.EMPTY_STRING_ARRAY))
							.map(File::new)
							.collect(Collectors.toList()));

			base.setCapability(ChromeOptions.CAPABILITY, options);

			return configureLogging(addProxy(base, proxy), logs, extras);
		}

		@Override
		public WebDriver driver(DesiredCapabilities capabilities) {
			return new ChromeDriver(capabilities);
		}
	},
	HTMLUNIT {
		@Override
		public DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras, LoggingPreferences logs) {
			DesiredCapabilities base = extras.boolValue("disableJs", false) ? DesiredCapabilities.htmlUnit() : DesiredCapabilities.htmlUnitWithJs();
			String browserName = extras.getString(BROWSER_NAME, null);
			try {
				if (!StringUtil.isWhite(browserName)) {
					BrowserVersion v = ClassUtil.fieldValue(BrowserVersion.class, browserName);
					base.setCapability("htmlunit.browser", v);
				}
			} catch (NoSuchFieldException e) {
				throw new WebDriverException("Couldn't find browser: " + browserName, e);
			}
			return configureLogging(addProxy(base, proxy), logs, extras);
		}

		@Override
		public WebDriver driver(DesiredCapabilities capabilities) {
			HtmlUnitDriver d;
			BrowserVersion v;
			if ((v = (BrowserVersion) capabilities.getCapability("htmlunit.browser")) != null) {
				d = new HtmlUnitDriver(v);
				d.setJavascriptEnabled(capabilities.isJavascriptEnabled());
				Proxy p;
				if ((p = (Proxy) capabilities.getCapability(PROXY)) != null) {
					d.setProxySettings(p);
				}
			} else {
				d = new HtmlUnitDriver(capabilities);
			}
			return d;
		}
	};
	/*,
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
*/
	private static final String BINARY = "binary";
	private static final String EXECUTABLE_SUFFIX = "_HOME";
	private static final char PATH_SEPARATOR = ':';
	private static final char NEW_LINE = '\n';
	private static final String STARTPAGE = "startpage";
	private static final String submap_LOGGING = "logging.";

	public abstract DesiredCapabilities buildCapabilities(Proxy proxy, Configuration extras, LoggingPreferences logs);

	public abstract WebDriver driver(DesiredCapabilities capabilities);

	private static DesiredCapabilities addProxy(DesiredCapabilities capabilities, Proxy proxy) {
		if (proxy != null) {
			capabilities.setCapability(PROXY, proxy);
		}
		return capabilities;
	}

	private static DesiredCapabilities configureLogging(DesiredCapabilities capabilities, LoggingPreferences logs, Configuration extras) {
		Configuration loggingConf = extras.submap(submap_LOGGING);
		LoggingPreferences prefs = loggingConf.isEmpty() ? logs : SeleniumContext.buildLoggingPreferences.apply(loggingConf);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);
		return capabilities;
	}
}
