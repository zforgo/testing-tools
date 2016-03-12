package hu.zforgo.testing.testng;

import hu.zforgo.testing.context.TestingToolsContext;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextConfigTest {

	@Test
	public void bootstrapTest() {
		assertThat(TestingToolsContext.getInstance()).isInstanceOf(TestingToolsContext.class);
		assertThat(TestNGToolsContext.getInstance()).isInstanceOf(TestNGToolsContext.class);
	}
}
