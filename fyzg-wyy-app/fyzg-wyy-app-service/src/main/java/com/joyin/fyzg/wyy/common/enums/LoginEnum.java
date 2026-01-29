package com.joyin.fyzg.wyy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/>
 *
 * @author pidong
 * @date 2023/11/17 16:07
 */
@Getter
@AllArgsConstructor
public enum LoginEnum {

    LOGIN("LOGIN", "登录"), LOGOUT("LOGOUT", "登出"), SUCCESS("SUCCESS", "成功"), FAIL("FAIL", "失败");

    private String type;
    private String desc;
}
