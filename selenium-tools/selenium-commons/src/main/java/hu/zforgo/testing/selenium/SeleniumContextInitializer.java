package hu.zforgo.testing.selenium;

import hu.zforgo.testing.context.ContextInitializationException;
import hu.zforgo.testing.context.ContextInitializationFailure;
import hu.zforgo.testing.context.ContextInitializer;
import hu.zforgo.testing.context.TestingToolsContext;
import hu.zforgo.testing.tools.configuration.Configuration;

public class SeleniumContextInitializer implements ContextInitializer{

	@Override
	public void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure {

	}

	@Override
	public void shutdown() throws Exception {

	}
}
