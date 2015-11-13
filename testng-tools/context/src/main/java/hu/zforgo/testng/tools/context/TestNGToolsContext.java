package hu.zforgo.testng.tools.context;

import hu.zforgo.testing.context.TestingToolsContext;


public class TestNGToolsContext extends TestingToolsContext {
//dummy
	public TestNGToolsContext() {
	}

	protected static synchronized void createContext() {
		TestingToolsContext.createContext(null);
	}

	protected synchronized void shutdown() throws Throwable {
		super.shutdown();
	}

	public static TestNGToolsContext getInstance() {
		return (TestNGToolsContext) TestingToolsContext.getInstance();
	}
}
