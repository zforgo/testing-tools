package hu.zforgo.testing.testng;

import hu.zforgo.testing.context.TestingToolsContext;


public class TestNGToolsContext extends TestingToolsContext {

	public TestNGToolsContext() {
	}

	protected static synchronized void createContext() {
		TestingToolsContext.createContext(null);
	}

	protected synchronized void shutdown() throws Throwable {
		super.shutdown();
	}

	public static TestNGToolsContext getInstance() {

		System.out.println("valami");
		System.out.println(Thread.currentThread().getStackTrace()[2]);
		System.out.println(Thread.currentThread().getStackTrace()[3]);
		return (TestNGToolsContext) TestingToolsContext.getInstance();
	}
}
