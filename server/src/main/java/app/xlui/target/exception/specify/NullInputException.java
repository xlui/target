package app.xlui.target.exception.specify;

import app.xlui.target.exception.common.AssertException;

/**
 * Null input exception, a subclass of AssertException. Always return 401.
 */
public class NullInputException extends AssertException {
	public NullInputException() {
		super();
	}

	public NullInputException(String message) {
		super(message);
	}

	public NullInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullInputException(Throwable cause) {
		super(cause);
	}

	protected NullInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
