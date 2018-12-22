package app.xlui.target.exception.specify;

import app.xlui.target.exception.common.AssertException;

/**
 * Not found exception, a subclass of AssertException. Always return 404.
 */
public class NotFoundException extends AssertException {
	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	protected NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
