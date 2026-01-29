package com.joyin.fyzg.wyy.common.enums;

import lombok.Getter;

/**
 * 是否是内部用户
 * <br/>
 *
 * @author pidong
 * @date 2021/5/27 9:49
 */
@Getter
public enum RbacInnerUserFlag {
    YES("1", "是"),
    NO("0", "否");

    private String value;
    private String desc;

    RbacInnerUserFlag(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
