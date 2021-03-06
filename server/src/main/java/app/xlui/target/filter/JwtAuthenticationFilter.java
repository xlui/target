package app.xlui.target.filter;

import app.xlui.target.entity.common.ApiResponse;
import app.xlui.target.entity.User;
import app.xlui.target.service.UserService;
import app.xlui.target.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Token authentication filter.
 *
 * First get username from token. And then query user entity from database. When
 * we have the user entity, do {@link JwtUtils#verify(String token, String username, String password)}.
 *
 * After passing the verification, we store user instance reference into
 * {@link SecurityContextHolder} for {@link app.xlui.target.annotation.CurrentUser}.
 *
 * @see app.xlui.target.annotation.CurrentUserMethodArgumentResolver
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static List<String> ignoreAuthorization = Arrays.asList("/login");
	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 获取 Token
		String uri = request.getRequestURI();
		boolean shouldIgnore = ignoreAuthorization.stream().anyMatch(uri::equals);
		if (!shouldIgnore) {
			String token = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (token != null) {
				try {
					auth(request, response, token);
				} catch (Exception e) {
					// 认证过程中发生异常
					ApiResponse apiResponse = new ApiResponse(HttpStatus.UNAUTHORIZED, "Token is invalid!");
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.setCharacterEncoding(CharsetUtil.UTF_8.toString());
					response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
					response.getOutputStream().println(new ObjectMapper().writeValueAsString(apiResponse));
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private void auth(HttpServletRequest request, HttpServletResponse response, String token) {
		String username = JwtUtils.username(token);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			User user = userService.findByUsernameChecked(username);
			// 进行认证
			JwtUtils.verify(token, user.getUsername(), user.getPassword());
			// 认证成功，将 Authentication 存入 SecurityContext
			var authentication = new UsernamePasswordAuthenticationToken(user, null, null);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}
}
