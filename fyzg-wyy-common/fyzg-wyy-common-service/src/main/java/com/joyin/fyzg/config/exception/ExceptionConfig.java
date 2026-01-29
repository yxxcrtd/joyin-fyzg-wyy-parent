package com.joyin.fyzg.config.exception;

import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.common.exception.WithTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理类
 * <br/>
 * @author pengzhen
 * @date 2019/7/23 0023 上午 9:45
 */
@ControllerAdvice
@Component
@Slf4j
public class ExceptionConfig {

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = Throwable.class)
	@ResponseBody
	public RestResponse<Object> handler(HttpServletRequest req, Throwable throwable) throws Exception {
		log.error("请求发送错误，地址如下：{},消息如下：{}", req.getRequestURL().toString(), throwable.getMessage(), throwable);
		throwable.printStackTrace();
		if (throwable instanceof WithTypeException) {
			return RestResponse.transWithCodeMsgEnum(((WithTypeException)throwable).getType());
		}else if(throwable != null && throwable.getCause() instanceof WithTypeException){
			return RestResponse.transWithCodeMsgEnum(((WithTypeException)throwable.getCause()).getType());
		}
		else {
			log.error("异常消息未映射", throwable);
			return RestResponse.error(throwable.getMessage());
		}
	}
}
