import hu.zforgo.testing.selenium.support.pagefactory.Pagepart;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import parts.Head;
import parts.SidePager;

public class Injectable extends ParentInjectable {

	@Pagepart
	Head h;

	@Pagepart
	SidePager s2;

//	@FindBy(id = "valami")
//	WebElement e1;

	@FindBy(css = ".kiadvany-lapozo-i2 a")
	WebElement e2;


}
