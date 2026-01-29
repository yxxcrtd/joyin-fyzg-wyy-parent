package com.joyin.fyzg.utils;/**
 * @Author Administrator
 * @Date 2022/5/7 18:00
 * @Description:
 */

import java.util.UUID;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2022/5/7 18:00
 */
public class TraceIdUtil {
	public static String getTraceId() {
		return UUID.randomUUID().toString();
	}
}
