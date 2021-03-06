package app.xlui.target.exception;

import app.xlui.target.entity.common.ApiResponse;
import app.xlui.target.exception.common.AssertException;
import app.xlui.target.exception.common.ServerError;
import app.xlui.target.exception.specify.ForbiddenException;
import app.xlui.target.exception.specify.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Controller advice. Resolve all specified exceptions thrown in controller methods.
 */
@RestControllerAdvice
public class GlobalControllerAdvice {
	// define a variable in property file to show exception log or not.
	@Value("${target.printStackTrace}")
	private boolean printStackTrace;

	@ExceptionHandler({
			MethodArgumentTypeMismatchException.class,
			HttpMessageNotReadableException.class,
			AssertException.class
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse assertFailed(Exception e) {
		if (printStackTrace) e.printStackTrace();
		return new ApiResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	/**
	 * Throw {@link MethodArgumentNotValidException} when validation on an argument
	 * annotated with {@code @Valid} fails. The default output is different from
	 * {@link ApiResponse}, so package it here.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse validateFailed(MethodArgumentNotValidException e) {
		if (printStackTrace) e.printStackTrace();
		List<ObjectError> errors = e.getBindingResult().getAllErrors();
		if (errors.size() > 1) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError error : errors) {
				sb.append("[").append(error.getDefaultMessage()).append("], ");
			}
			sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
			return new ApiResponse(HttpStatus.BAD_REQUEST, sb.toString());
		} else {
			return new ApiResponse(HttpStatus.BAD_REQUEST, errors.get(0).getDefaultMessage());
		}
	}

	@ExceptionHandler({MissingRequestHeaderException.class, UsernameNotFoundException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiResponse tokenAuthenticationFailed(Exception e) {
		if (printStackTrace) e.printStackTrace();
		return new ApiResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
	}

	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ApiResponse forbidden(Exception e) {
		if (printStackTrace) e.printStackTrace();
		return new ApiResponse(HttpStatus.FORBIDDEN, e.getMessage());
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse notFound(Exception e) {
		if (printStackTrace) e.printStackTrace();
		return new ApiResponse(HttpStatus.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ApiResponse notAllowed(Exception e) {
		if (printStackTrace) e.printStackTrace();
		return new ApiResponse(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}

	@ExceptionHandler({ServerError.class, ConstraintViolationException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse serverError(Exception e) {
		if (printStackTrace) e.printStackTrace();
		return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
}
