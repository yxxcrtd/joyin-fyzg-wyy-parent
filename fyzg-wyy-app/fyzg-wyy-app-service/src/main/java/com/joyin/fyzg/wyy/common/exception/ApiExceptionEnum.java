package com.joyin.fyzg.wyy.common.exception;

import com.joyin.fyzg.common.ResponseCode;
import com.joyin.fyzg.common.WithCodeMsgFormatEnum;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2023/10/24 19:13
 */
public enum ApiExceptionEnum implements WithCodeMsgFormatEnum {
	TOKEN_NOT_FOUND(ResponseCode.RE_AUTH_ERROR.code, "请求地址【{0}】请登录！"){
		@Override
		public String getMsg() {
			return "请登录！";
		}
	},
	TOKEN_VALIDATE(ResponseCode.RE_AUTH_ERROR.code, "账号【{0}】会话校验失败！"){
		@Override
		public String getMsg() {
			return "会话校验失败！";
		}
	},
	TOKEN_IS_EXPIRED(ResponseCode.RE_AUTH_ERROR.code, "账号【{0}】会话已过期！"){
		@Override
		public String getMsg() {
			return "会话已过期！";
		}
	},
	;

	public final String code;
	public final String format;
	public String errorMsg;

	@Override
	public String getFormat() {
		return this.format;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMsg() {
		return this.errorMsg;
	}

	private ApiExceptionEnum(String code, String format) {
		this.code = code;
		this.format = format;
	}
}