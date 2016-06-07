package hu.zforgo.testing.junit;

import hu.zforgo.testing.context.TestingToolsContext;
import org.junit.runner.Description;
import org.junit.runner.Result;

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;


public class JUnitToolsContext extends TestingToolsContext {

	public JUnitToolsContext() {
	}

	protected static synchronized void createContext(Description description) {
		TestingToolsContext.createContext(description);
	}

	protected synchronized void shutdown(Result result) throws Throwable {
		shutdown();
	}

	public static JUnitToolsContext getInstance() {
		System.out.println("valami");
		System.out.println(Thread.currentThread().getStackTrace()[2]);
		System.out.println(Thread.currentThread().getStackTrace()[3]);
		return (JUnitToolsContext) TestingToolsContext.getInstance();
	}

}
