package hu.zforgo.testing;

import hu.zforgo.testing.junit.JUnitToolsContext;
import org.junit.Test;

public class CallerTest {

	@Test
	public void valami() {
		JUnitToolsContext.getInstance();
	}
}
