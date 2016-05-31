import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

public class InjectTest {


	@Test
	public void test() {
		WebDriver d = new FirefoxDriver();
		d.get("http://index.hu");
		Injectable o = PageFactory.initElements(d, Injectable.class);
		d.quit();
	}
}
