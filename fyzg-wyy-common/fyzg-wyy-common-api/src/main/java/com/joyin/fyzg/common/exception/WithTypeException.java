package com.joyin.fyzg.common.exception;

import com.joyin.fyzg.common.WithCodeMsgEnum;
import com.joyin.fyzg.common.WithCodeMsgFormatEnum;

import java.util.Optional;

/**
 * 标识异常的接口，异常实现该接口，提供getType方法便于统一异常处理
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/23 0023 上午 10:11
 */
public class WithTypeException extends RuntimeException {

	private final WithCodeMsgEnum type;

	public WithCodeMsgEnum getType() {
		return this.type;
	}

	public WithTypeException(String msg) {
		this(msg, null);
	}

	public WithTypeException(String msg, Throwable e) {
		this(new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return "";
			}

			@Override
			public String getMsg() {
				return msg;
			}
		}, e);
	}

	public WithTypeException(WithCodeMsgEnum type) {
		this(type, null);
	}

	public WithTypeException(WithCodeMsgEnum type, Throwable e) {
		super(type.getMsg(), e);
		this.type = type;
		//这里打印异常堆栈消息
		Optional.ofNullable(e).ifPresent(i -> i.printStackTrace());
	}

	public WithTypeException(WithCodeMsgFormatEnum type, Object... arguments) {
		this(type.format(arguments), null);
	}

	public WithTypeException(WithCodeMsgFormatEnum type, Throwable e, Object... arguments) {
		this(type.format(arguments), e);
	}
}
