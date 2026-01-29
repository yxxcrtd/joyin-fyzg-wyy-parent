package com.joyin.fyzg.wyy.config.cas.constant;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2023/10/31 14:12
 */
public class Constant {

	public static final String CURRENT_USER = "CURRENT_USER";
	public static final String ST_TOKENS = "TICKET_TOKENS";
	/*已登录用户的tokenHASH集合(sessionId+JWT)*/
	public static final String USER_LOGIN_TOKENS = "USER_LOGIN_TOKENS";
	public static final String REDIRECT_URL = "joyinRedirectUrl";
	/*会话失效（cas登出或过期）SET集合*/
	public static final String EXPIRE_LOGIN_TOKENS = "EXPIRE_LOGIN_TOKENS";
}
