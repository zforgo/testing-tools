package hu.zforgo.junit.tools.context;

import hu.zforgo.testing.context.TestingToolsContext;
import org.junit.runner.Description;
import org.junit.runner.Result;


public class JUnitToolsContext extends TestingToolsContext {

	private JUnitToolsContext() {
	}

	protected static synchronized void createContext(Description description) {
		TestingToolsContext.createContext(description);
	}

	protected synchronized void shutdown(Result result) throws Throwable {
		shutdown();
	}

	public static JUnitToolsContext getInstance() {
		return (JUnitToolsContext) TestingToolsContext.getInstance();
	}

}
