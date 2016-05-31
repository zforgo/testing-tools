package parts;

import hu.zforgo.testing.selenium.support.pagefactory.Pagepart;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SidePager {

	@FindBy(css = "a.lapozo-fel")
	public WebElement up;
	@FindBy(css = "a.lapozo-le")
	public WebElement down;

	@Pagepart
	private Head head;
}
