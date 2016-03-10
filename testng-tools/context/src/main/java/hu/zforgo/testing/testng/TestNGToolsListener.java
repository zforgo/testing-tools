package hu.zforgo.testing.testng;

import org.testng.IExecutionListener;


public class TestNGToolsListener implements IExecutionListener {

	@Override
	public void onExecutionStart() {
		TestNGToolsContext.createContext();
	}

	@Override
	public void onExecutionFinish() {

	}
}
