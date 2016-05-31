package parts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Lead {

	@FindBy(css = "article.vezeto-anyag")
	public WebElement leadArticle;
}
