package com.joyin.fyzg.config.encrypt.interceptor;

import com.joyin.fyzg.config.encrypt.constant.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2023/10/20 16:49
 */
public class FeignEncryptRequestInterceptor implements RequestInterceptor {
	/**
	 * 所有用feign发出的请求的拦截器，注意是feign作为客户端发出请求的，而不是服务端
	 *
	 * @param template
	 */
	@Override
	public void apply(RequestTemplate template) {
		//标识这个请求来源是从feign来的
		template.header(Constants.FROM_FEIGN_KEY, Constants.FROM_FEIGN_VALUE);
	}
}
