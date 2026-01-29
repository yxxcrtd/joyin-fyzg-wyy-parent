package com.joyin.fyzg.wyy.common.enums;/**
 * @Author Administrator
 * @Date 2023/11/1 10:37
 * @Description:
 */

import com.joyin.fyzg.common.WithCodeMsgFormatEnum;
import lombok.Getter;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2023/11/1 10:37
 */
@Getter
public enum LoginType implements WithCodeMsgFormatEnum {

	LOGIN_SUCCESS("LOGIN-200000" , "登录成功"),
	LOGIN_USER_FIRST_LOGIN_FAIL("LOGIN-200001" , "用户首次登录！请修改密码"),
	LOGIN_USER_PWD_VALID_FAIL("LOGIN-200002", "用户密码已过期！请重新修改密码"),
	LOGIN_USER_DISABLED_FAIL("LOGIN-200003", "用户被禁用，请联系管理员启用用户"),
	LOGIN_USER_NAME_OR_PASSWORD_FAIL("LOGIN-200004", "用户名或密码错误"),
	LOGIN_USER_LOCKED_FAIL("LOGIN-200005", "用户被锁定，请联系管理员解锁或者等待{0}分钟后重新登录"),
	LOGIN_PAGE_FAIL("LOGIN-200006", "{0}"),
	LOGIN_VERIFY_CODE_FAIL("LOGIN-200007", "验证码错误"),

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


	private LoginType(String code, String format) {
		this.code = code;
		this.format = format;
	}

}
