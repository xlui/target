package app.xlui.target.annotation;

import app.xlui.target.entity.User;
import app.xlui.target.exception.specify.NullInputException;
import app.xlui.target.util.AssertUtils;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(CurrentUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AssertUtils.requireNotNull(authentication, () -> new NullInputException("Cannot get authentication!"));
		return authentication.getPrincipal();
	}
}
