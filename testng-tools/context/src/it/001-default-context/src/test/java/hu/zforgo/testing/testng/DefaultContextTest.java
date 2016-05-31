package hu.zforgo.testing.testng;


import hu.zforgo.testing.context.TestingToolsContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultContextTest {

	@Test
	public void isDefaultContext() {
		assertThat(TestingToolsContext.getInstance()).isInstanceOf(TestingToolsContext.class);
		assertThat(TestNGToolsContext.getInstance()).isInstanceOf(TestNGToolsContext.class);

	}

//	@Test(expectedExceptions = {ClassCastException.class})
//	public void classCastTest() {
//		TestNGToolsContext.getInstance();
//	}
}
