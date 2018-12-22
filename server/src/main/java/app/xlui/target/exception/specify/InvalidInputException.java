package app.xlui.target.exception.specify;

import app.xlui.target.exception.common.AssertException;

/**
 * Invalid Input Exception, a subclass of AssertException, always return 401.
 */
public class InvalidInputException extends AssertException {
	protected InvalidInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidInputException(Throwable cause) {
		super(cause);
	}

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException() {
		super();
	}
}
