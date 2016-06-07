import hu.zforgo.testing.testng.TestNGToolsContext;
import org.testng.annotations.Test;

/**
 * Created by spinner on 5/31/16.
 */
public class CallerTest {

	@Test
	public void test() {
		TestNGToolsContext.getInstance();
	}
}
