package hu.zforgo.testing.tools.context;

import hu.zforgo.junit.tools.context.JUnitToolsContext;
import hu.zforgo.testing.context.ContextInitializationException;
import hu.zforgo.testing.context.ContextInitializationFailure;
import hu.zforgo.testing.context.ContextInitializer;
import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testing.tools.configuration.Configuration;

import static org.assertj.core.api.Assertions.assertThat;


public class JunitToolsInitializer implements ContextInitializer {


	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
		DummyContextObject.create(true);
		assertThat(context).isExactlyInstanceOf(JUnitToolsContext.class);
	}

	@Override
	public void shutdown() throws Exception {

	}

}
