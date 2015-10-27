package hu.zforgo.junit.tools.context;

public class ContextInitializationFailure extends Error {
	private static final long serialVersionUID = 1L;

	public ContextInitializationFailure() {
	}

	public ContextInitializationFailure(String message) {
		super(message);
	}

	public ContextInitializationFailure(String message, Throwable cause) {
		super(message, cause);
	}

	public ContextInitializationFailure(Throwable cause) {
		super(cause);
	}

	protected ContextInitializationFailure(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
