package com.joyin.fyzg.config.logback;/**
 * @Author Administrator
 * @Date 2022/5/7 18:32
 * @Description:
 */

import com.joyin.fyzg.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2022/5/7 18:32
 */
@Configuration
public class WebAppConfigurer
		extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 可添加多个
		registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
	}

}
