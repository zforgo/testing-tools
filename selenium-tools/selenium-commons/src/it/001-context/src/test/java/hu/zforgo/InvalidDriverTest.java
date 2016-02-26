package hu.zforgo;

import hu.zforgo.testing.selenium.DriverSetup;
import hu.zforgo.testing.selenium.SeleniumContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class InvalidDriverTest {

	private DriverSetup driverSetup;

	public InvalidDriverTest(DriverSetup driverSetup) {
		this.driverSetup = driverSetup;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> allDrivers() {
		return Stream.of(DriverSetup.values()).map(e -> new Object[]{e}).collect(Collectors.toList());
	}

	@Test(expected = Exception.class)
	public void invalidExtensions() {
		SeleniumContext.getInstance().driver(driverSetup);
	}

}
