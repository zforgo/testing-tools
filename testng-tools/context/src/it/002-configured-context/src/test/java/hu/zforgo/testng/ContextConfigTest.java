package hu.zforgo.testng;

import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testng.tools.context.TestNGToolsContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextConfigTest {

	@Test
	public void bootstrapTest() {
		assertThat(TestingToolsContext.getInstance()).isInstanceOf(TestingToolsContext.class);
		assertThat(TestNGToolsContext.getInstance()).isInstanceOf(TestNGToolsContext.class);
	}
}
