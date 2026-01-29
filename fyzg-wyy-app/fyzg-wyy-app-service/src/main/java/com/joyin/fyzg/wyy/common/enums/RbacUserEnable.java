package com.joyin.fyzg.wyy.common.enums;

import lombok.Getter;

/**
 * 用户是否启用
 * <br/>
 *
 * @author pidong
 * @date 2021/5/27 9:49
 */
@Getter
public enum RbacUserEnable {
    ENABLE("1","启用"),
    DISABLE("0","禁用");

    private String value;
    private String desc;

    RbacUserEnable(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
