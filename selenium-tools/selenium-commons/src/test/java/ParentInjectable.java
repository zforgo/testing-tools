import hu.zforgo.testing.selenium.support.pagefactory.Pagepart;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import parts.SidePager;

public class ParentInjectable {

	@Pagepart
	SidePager s;

	@FindBy(id = "icon-nagyito")
	WebElement focus;
}
