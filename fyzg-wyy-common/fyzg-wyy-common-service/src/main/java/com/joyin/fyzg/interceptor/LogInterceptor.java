package com.joyin.fyzg.interceptor;/**
 * @Author Administrator
 * @Date 2022/5/7 17:51
 * @Description:
 */

import com.joyin.fyzg.common.constant.Constants;
import com.joyin.fyzg.utils.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.tomcat.util.http.MimeHeaders;
//import java.lang.reflect.Field;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2022/5/7 17:51
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//如果有上层调用就用上层的ID
		String traceId = request.getHeader(Constants.TRACE_ID);
		if (traceId == null) {
			traceId = TraceIdUtil.getTraceId();
		}

		MDC.put(Constants.TRACE_ID, traceId);
		return true;
	}

//	private void reflectSetHeader(HttpServletRequest request, String key, String value){
//		Class<? extends HttpServletRequest> requestClass = request.getClass();
//		try {
//			Field requestField = requestClass.getDeclaredField("request");
//			requestField.setAccessible(true);
//			Object requestObj = requestField.get(request);
//			Field coyoteRequestField = requestObj.getClass().getDeclaredField("coyoteRequest");
//			coyoteRequestField.setAccessible(true);
//			Object coyoteRequestObj = coyoteRequestField.get(requestObj);
//			Field headersField = coyoteRequestObj.getClass().getDeclaredField("headers");
//			headersField.setAccessible(true);
//			headersField.get(coyoteRequestObj);
//			MimeHeaders headersObj = (MimeHeaders)headersField.get(coyoteRequestObj);
//			headersObj.removeHeader(key);
//			headersObj.addValue(key).setString(value);
//		} catch (Exception e) {
//			log.error("reflect set header {} error {}", key, e);
//		}
//	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//调用结束后删除
		MDC.remove(Constants.TRACE_ID);
	}
}
