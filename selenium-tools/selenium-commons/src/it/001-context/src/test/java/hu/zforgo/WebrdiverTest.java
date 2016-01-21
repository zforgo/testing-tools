package hu.zforgo;

import hu.zforgo.testing.selenium.DriverSetup;
import hu.zforgo.testing.selenium.SeleniumContext;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class WebrdiverTest {

//	@Test
	public void localDriverTests() {
		WebDriver d = SeleniumContext.getInstance().driver(DriverSetup.CHROME);
		System.out.println(d);
	}
}
