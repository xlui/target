package app.xlui.target.entity.common;

import org.springframework.http.HttpStatus;

/**
 * Common response of web controller method.
 */
public class ApiResponse {
	private HttpStatus status;
	private Object content;

	public ApiResponse() {
	}

	public ApiResponse(HttpStatus status, Object content) {
		this.status = status;
		this.content = content;
	}

	public static ApiResponse of(HttpStatus status, Object content) {
		return new ApiResponse(status, content);
	}

	@Override
	public String toString() {
		return "ApiResponse[status = " + status + ", content = " + content + "]";
	}

	public HttpStatus getStatus() {
		return status;
	}

	public ApiResponse setStatus(HttpStatus status) {
		this.status = status;
		return this;
	}

	public Object getContent() {
		return content;
	}

	public ApiResponse setContent(Object content) {
		this.content = content;
		return this;
	}
}
