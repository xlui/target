package app.xlui.target.annotation;

import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.annotation.*;

/**
 * Annotation for injecting current user entity to controller method's argument.
 *
 * Usage:
 * <code>@RequestMapping("/path") public Object someMethod(@CurrentUser User user);
 * </code>
 *
 * This annotation will be resolved by {@link CurrentUserMethodArgumentResolver},
 * which will get the reference of user entity from {@link SecurityContextHolder},
 * and then inject it to the related method argument. The time when we put the
 * user entity class into {@link SecurityContextHolder} is among the token verification
 * with {@link app.xlui.target.filter.JwtAuthenticationFilter}. After passing token
 * validation, the user entity instance will be set into {@link SecurityContextHolder}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface CurrentUser {
}
