package hu.zforgo.junit.tools.context;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;


public class JUnitToolsListener extends RunListener {

	@Override
	public void testRunStarted(Description description) throws Exception {
		JUnitToolsContext.createContext(description);
	}

	@Override
	public void testRunFinished(Result result) {
		try {
			JUnitToolsContext.getInstance().shutdown(result);
		} catch (Throwable t) {
			//TODO find a better class
			throw new AssertionError(t);
		}

	}
}
