package hu.zforgo.testng;

import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testng.tools.context.TestNGToolsContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultContextTest {

	@Test
	public void isDefaultContext() {
		assertThat(TestingToolsContext.getInstance()).isInstanceOf(TestingToolsContext.class);
	}

	@Test(expectedExceptions = {ClassCastException.class})
	public void classCastTest() {
		TestNGToolsContext.getInstance();
	}
}
