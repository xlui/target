package app.xlui.target.exception.specify;

import app.xlui.target.exception.common.AssertException;

public class TokenAuthException extends AssertException {
	public TokenAuthException() {
		super();
	}

	public TokenAuthException(String message) {
		super(message);
	}

	public TokenAuthException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenAuthException(Throwable cause) {
		super(cause);
	}

	protected TokenAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
