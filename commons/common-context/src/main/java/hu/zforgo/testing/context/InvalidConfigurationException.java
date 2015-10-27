package hu.zforgo.testing.context;

//TODO find a better name
public class InvalidConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidConfigurationException() {
		super();
	}

	public InvalidConfigurationException(String s) {
		super(s);
	}


	public InvalidConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidConfigurationException(Throwable cause) {
		super(cause);
	}

}
