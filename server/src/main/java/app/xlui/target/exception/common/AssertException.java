package app.xlui.target.exception.common;

/**
 * Common exceptions, provide a abstract interface for method arguments. In order
 * to serve mode universal exception logical
 */
public class AssertException extends RuntimeException {
	public AssertException() {
		super();
	}

	public AssertException(String message) {
		super(message);
	}

	public AssertException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssertException(Throwable cause) {
		super(cause);
	}

	protected AssertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
