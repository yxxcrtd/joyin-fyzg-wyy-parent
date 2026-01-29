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
public enum RbacUserLocked {
    UN_LOCKED("0","未锁定"),
    LOCKED("1","锁定");

    private String value;
    private String desc;

    RbacUserLocked(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
