package com.joyin.fyzg.config.logback;/**
 * @Author Administrator
 * @Date 2022/5/7 19:43
 * @Description:
 */

import com.joyin.fyzg.interceptor.FeignAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2022/5/7 19:43
 */

@Configuration  // 全局配置
public class FeignConfig {

	/**
	 * 自定义拦截器
	 * @return
	 */
	@Bean
	public FeignAuthRequestInterceptor feignAuthRequestInterceptor(){
		return new FeignAuthRequestInterceptor();
	}

}
