package hu.zforgo.testing.junit;

import hu.zforgo.testing.context.DummyContextObject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InitTest {

	@Test
	public void dummyContextObjectTest() {
		assertThat(DummyContextObject.getInstance().isEnabled()).isTrue();
	}
}
