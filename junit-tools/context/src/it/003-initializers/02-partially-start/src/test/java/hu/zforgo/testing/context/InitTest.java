package hu.zforgo.testing.context;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InitTest {
	@Test
	public void dummyContextObjectTest() {
		assertThat(DummyContextObject.getInstance().isEnabled()).isTrue();
	}
}
