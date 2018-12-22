package app.xlui.target.config;

import app.xlui.target.annotation.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web Mvc Configurations. Thanks to Java 1.8, now {@link WebMvcConfigurer} has its
 * default implements. So we needn't to inherit
 * {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter}
 * which is now being marked as deprecated.
 *
 * In this configuration class, we add our argument resolver for
 * {@link app.xlui.target.annotation.CurrentUser}, and configure a global cors strategy.
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Autowired
	private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(currentUserMethodArgumentResolver);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
				.allowedHeaders("Authorization", "Content-Type")
				.maxAge(600)
				.allowCredentials(false);
	}
}
