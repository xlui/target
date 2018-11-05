package app.xlui.target.exception;

import app.xlui.target.entity.ApiResponse;
import app.xlui.target.exception.common.AssertException;
import app.xlui.target.exception.common.ServerError;
import app.xlui.target.exception.specify.TokenAuthException;
import app.xlui.target.exception.specify.TokenParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse parseRequestBodyError(Exception ex) {
		return new ApiResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler({MissingRequestHeaderException.class, TokenAuthException.class, TokenParseException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiResponse tokenAuthenticationFailed(Exception e) {
		return new ApiResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
	}

	@ExceptionHandler(AssertException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse assertFailed(AssertException ex) {
		return new ApiResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(ServerError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse serverError(Exception ex) {
		return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}
}