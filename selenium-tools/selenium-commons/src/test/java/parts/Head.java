package parts;

import hu.zforgo.testing.selenium.support.pagefactory.Pagepart;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Head {

	@FindBy(css = "a.logo")
	public WebElement logoLink;

	@FindBy(css = "#index nav")
	public WebElement navLinks;

	@FindBy(css = "#index .social-tools")
	public WebElement socialTools;

	@Pagepart
	private Lead lead;
}
