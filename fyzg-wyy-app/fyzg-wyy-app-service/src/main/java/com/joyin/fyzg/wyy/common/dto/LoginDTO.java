package com.joyin.fyzg.wyy.common.dto;

import com.joyin.fyzg.common.WithCodeMsgEnum;
import lombok.Builder;
import lombok.Data;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2023/11/1 10:37
 */
@Builder
@Data
public class LoginDTO {

	private WithCodeMsgEnum loginType;
	private String token;

	public LoginDTO(WithCodeMsgEnum loginType) {
		this.loginType = loginType;
	}

	public LoginDTO(WithCodeMsgEnum loginType, String token) {
		this.loginType = loginType;
		this.token = token;
	}


}
