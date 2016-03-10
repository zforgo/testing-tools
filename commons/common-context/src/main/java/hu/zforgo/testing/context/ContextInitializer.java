package hu.zforgo.testing.context;


import hu.zforgo.testing.configuration.Configuration;

public interface ContextInitializer {

	void init(TestingToolsContext context, Configuration contextConfig) throws ContextInitializationException, ContextInitializationFailure;
	void shutdown() throws Exception;
}
