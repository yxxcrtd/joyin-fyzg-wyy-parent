package com.joyin.fyzg.wyy.config.cors;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * 跨域访问配置
 * <br/>
 *
 * @author pengzhen
 * @date 2020/3/30 0030 下午 4:26
 */
@Component
public class CorsConfig {

	@Bean
	public FilterRegistrationBean corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		//是否允许发送Cookie的值
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader(CorsConfiguration.ALL);
		config.addAllowedMethod(CorsConfiguration.ALL);
		// 允许前端访问刷新token的响应头
		config.addExposedHeader("refresh-token");

		config.setMaxAge(600L);
		source.registerCorsConfiguration("/**", config);

		// 这里需要通过FilterRegistrationBean来指定CorsFilter的优先级，原来那种方式在JwtAuthorizationTokenFilter的直接write还是会出现跨域问题
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter( new CorsFilter(source));
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registrationBean.setName("CorsFilter");
		return  registrationBean;
	}


}