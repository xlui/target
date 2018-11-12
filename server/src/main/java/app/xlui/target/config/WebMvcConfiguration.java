package app.xlui.target.config;

import app.xlui.target.annotation.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
