package com.joyin.fyzg.common.exception;

import com.joyin.fyzg.common.WithCodeMsgEnum;
import com.joyin.fyzg.common.WithCodeMsgFormatEnum;

/**
 * 通用的异常
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/18 0018 下午 3:45
 */
public class CommonException extends WithTypeException {
	private static final long serialVersionUID = 936915964862903634L;

	public CommonException(String msg) {
		super(msg);
	}

	public CommonException(String msg, Throwable e) {
		super(msg, e);
	}

	public CommonException(WithCodeMsgEnum type, Throwable e) {
		super(type,e);
	}

	public CommonException(WithCodeMsgFormatEnum type, Object... arguments) {
		super(type.format(arguments));
	}
}
