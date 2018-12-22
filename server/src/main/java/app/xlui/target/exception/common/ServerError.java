package app.xlui.target.exception.common;

/**
 * Another skeleton for unchecked exceptions.
 */
public class ServerError extends RuntimeException {
	public ServerError() {
		super();
	}

	public ServerError(String message) {
		super(message);
	}

	public ServerError(String message, Throwable cause) {
		super(message, cause);
	}

	public ServerError(Throwable cause) {
		super(cause);
	}

	protected ServerError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
