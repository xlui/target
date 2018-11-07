package app.xlui.target.exception.specify;

import app.xlui.target.exception.common.AssertException;

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
