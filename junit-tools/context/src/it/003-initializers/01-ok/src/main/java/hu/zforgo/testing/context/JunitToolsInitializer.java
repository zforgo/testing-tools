package hu.zforgo.testing.context;


import hu.zforgo.testing.configuration.Configuration;
import hu.zforgo.testing.junit.JUnitToolsContext;

import static org.assertj.core.api.Assertions.assertThat;

public class JunitToolsInitializer implements ContextInitializer {


	@Override
	public void init( TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
		DummyContextObject.create(true);
		assertThat(context).isExactlyInstanceOf(JUnitToolsContext.class);
	}

	@Override
	public void shutdown() throws Exception {

	}

}
