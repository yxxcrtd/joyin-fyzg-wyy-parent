package com.joyin.fyzg.interceptor;/**
 * @Author Administrator
 * @Date 2022/5/7 19:09
 * @Description:
 */



import com.joyin.fyzg.common.constant.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2022/5/7 19:09
 */
public class FeignAuthRequestInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate template) {
		String traceId = MDC.get(Constants.TRACE_ID);
		//当前线程调用中有traceId，则将该traceId进行透传
		if (traceId != null) {
			//添加请求体
			template.header(Constants.TRACE_ID, traceId);
		}

	}
}
