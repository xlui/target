package app.xlui.target.exception.specify;

import app.xlui.target.exception.common.AssertException;

/**
 * Forbidden exception, a subclass of AssertException. Always return 403.
 */
public class ForbiddenException extends AssertException {
	public ForbiddenException() {
		super();
	}

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
	}

	protected ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
