package com.joyin.fyzg.config.encrypt.ignore;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.joyin.fyzg.config.encrypt.constant.Constants.FROM_FEIGN_KEY;
import static com.joyin.fyzg.config.encrypt.constant.Constants.FROM_FEIGN_VALUE;

/**
 * 请求响应加解密忽略判断接口默认实现，仅判断feign调用的方式，特殊情况各服务自定义实现，比如网关服务
 * <br/>
 *
 * @author Administrator
 * @date 2023/10/21 11:16
 */
@Component
public class DefaultIgnoreCheck implements IgnoreCheck {
	@Override
	public Boolean ignore(HttpServletRequest req) {
		// feign调用不加解密
		return FROM_FEIGN_VALUE.equals(req.getHeader(FROM_FEIGN_KEY));
	}
}
