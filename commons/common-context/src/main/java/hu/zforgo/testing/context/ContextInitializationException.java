package hu.zforgo.testing.context;

public class ContextInitializationException extends Exception {

	public ContextInitializationException() {
		super();
	}

	public ContextInitializationException(String message) {
		super(message);
	}

	public ContextInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContextInitializationException(Throwable cause) {
		super(cause);
	}

	protected ContextInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
