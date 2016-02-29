package hu.zforgo;

import hu.zforgo.testing.selenium.DriverSetup;
import hu.zforgo.testing.selenium.SeleniumContext;
import hu.zforgo.testing.tools.configuration.ConfigurationFactory;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ValidDriverTest {

	private static final String START_PAGE = "https://github.com/zforgo/testing-tools";
	private DriverSetup driverSetup;
	private WebDriver driver;

	public ValidDriverTest(DriverSetup driverSetup) {
		this.driverSetup = driverSetup;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> allDrivers() {
		return Stream.of(DriverSetup.values()).map(e -> new Object[]{e}).collect(Collectors.toList());
	}

	@Before
	public void initDriver() {
		driver = SeleniumContext.getInstance().driver(driverSetup);
	}

	@After
	public void quitDriver() {
		driver.quit();
	}


	@Test
	public void baseLoalDriverTests() {
		// extensions and preferences checks
		assertThat(driver).as("Checking %s driver state.", driverSetup.name()).isNotNull();
	}

	@Test
	public void loggingPreferencesTest() {
		Assume.assumeFalse(String.format("The driver [%s] doesn't support loggingPreferences option. Ignored.", driverSetup.name()), driverSetup == DriverSetup.HTMLUNIT);
		// logging preferences checks
		Logs logs = driver.manage().logs();
		assertThat(logs.get(LogType.DRIVER)).isEmpty();
		if (driverSetup == DriverSetup.FIREFOX) {
			assertThat(logs.get(LogType.BROWSER)).isNotEmpty();
		}
		if (driverSetup == DriverSetup.CHROME) {
			assertThat(logs.get(LogType.PERFORMANCE)).isNotEmpty();
		}
	}

	@Test
	public void startPageTest() {
		Assume.assumeFalse(String.format("The driver [%s] doesn't support startPage option. Ignored.", driverSetup.name()), driverSetup == DriverSetup.HTMLUNIT);
		// startpage check
		assertThat(driver.getCurrentUrl()).as("Check Startup page for driver: %s", driverSetup.name()).isEqualTo(START_PAGE);
	}


}
