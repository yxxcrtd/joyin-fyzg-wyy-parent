package com.joyin.fyzg.config.encrypt.ignore;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求响应加解密忽略判断接口，一般用以判断网关转发的情况下忽略加解密
 * <br/>
 *
 * @author Administrator
 * @date 2023/10/21 11:14
 */
public interface IgnoreCheck {

	Boolean ignore(HttpServletRequest req);
}
