package hu.zforgo.testing.context.shared;

import hu.zforgo.testing.context.ContextInitializationException;
import hu.zforgo.testing.context.ContextInitializationFailure;
import hu.zforgo.testing.context.ContextInitializer;
import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testing.configuration.Configuration;

public class BadInitializer implements ContextInitializer {

	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {
		throw new ContextInitializationException("Always thrown");
	}

	@Override
	public void shutdown() throws Exception {

	}
}
