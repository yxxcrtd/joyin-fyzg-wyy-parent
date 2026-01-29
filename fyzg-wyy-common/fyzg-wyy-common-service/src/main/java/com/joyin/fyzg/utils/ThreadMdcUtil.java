package com.joyin.fyzg.utils;/**
 * @Author Administrator
 * @Date 2022/5/7 17:57
 * @Description:
 */

import com.joyin.fyzg.common.constant.Constants;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2022/5/7 17:57
 */
public class ThreadMdcUtil {
	public static void setTraceIdIfAbsent() {
		if (MDC.get(Constants.TRACE_ID) == null) {
			MDC.put(Constants.TRACE_ID, TraceIdUtil.getTraceId());
		}
	}

	public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
		return () -> {
			if (context == null) {
				MDC.clear();
			} else {
				MDC.setContextMap(context);
			}
			setTraceIdIfAbsent();
			try {
				return callable.call();
			} finally {
				MDC.clear();
			}
		};
	}

	public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
		return () -> {
			if (context == null) {
				MDC.clear();
			} else {
				MDC.setContextMap(context);
			}
			setTraceIdIfAbsent();
			try {
				runnable.run();
			} finally {
				MDC.clear();
			}
		};
	}
}
