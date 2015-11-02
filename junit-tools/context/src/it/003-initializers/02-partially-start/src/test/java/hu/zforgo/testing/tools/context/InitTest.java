package hu.zforgo.testing.tools.context;

import org.assertj.core.api.StrictAssertions;
import org.junit.Test;

public class InitTest {
	@Test
	public void dummyContextObjectTest() {
		StrictAssertions.assertThat(DummyContextObject.getInstance().isEnabled()).isTrue();
	}
}
