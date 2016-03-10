package hu.zforgo.testing.context.shared;

import hu.zforgo.testing.context.ContextInitializationException;
import hu.zforgo.testing.context.ContextInitializationFailure;
import hu.zforgo.testing.context.ContextInitializer;
import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testing.configuration.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonInitializer implements ContextInitializer
{
	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
		assertThat(context).isInstanceOf(TestingToolsContext.class);
	}

	@Override
	public void shutdown() throws Exception {

	}
}
