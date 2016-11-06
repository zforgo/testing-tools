package hu.zforgo;

import com.google.common.base.Predicate;
import hu.zforgo.testing.selenium.DriverSetup;
import hu.zforgo.testing.selenium.SeleniumContext;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		return Stream.of(DriverSetup.values()).filter(d -> DriverSetup.HTMLUNIT != d).map(e -> new Object[]{e}).collect(Collectors.toList());
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

//	@Test
//	public void loggingPreferencesTest() {
//		Assume.assumeFalse(String.format("The driver [%s] doesn't support loggingPreferences option. Ignored.", driverSetup.name()), driverSetup == DriverSetup.HTMLUNIT);
//		// logging preferences checks
//		Logs logs = driver.manage().logs();
//		assertThat(logs.get(LogType.DRIVER)).isEmpty();
//		if (driverSetup == DriverSetup.FIREFOX) {
//			assertThat(logs.get(LogType.BROWSER)).isNotEmpty();
//		}
//		if (driverSetup == DriverSetup.CHROME) {
//			assertThat(logs.get(LogType.PERFORMANCE)).isNotEmpty();
//		}
//	}

	@Test
	public void startPageTest() {
		Assume.assumeFalse(String.format("The driver [%s] doesn't support startPage option. Ignored.", driverSetup.name()), driverSetup == DriverSetup.HTMLUNIT);
		// startpage check
		WebDriverWait w = new WebDriverWait(driver,2_000L);
		w.until(new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver input) {
				return input.getCurrentUrl().equals(START_PAGE);
			}
		});
		assertThat(driver.getCurrentUrl()).as("Check Startup page for driver: %s", driverSetup.name()).isEqualTo(START_PAGE);
	}


}
