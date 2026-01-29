package com.joyin.fyzg.wyy.common.enums;

import lombok.Getter;

/**
 * 用户类型
 * <br/>
 *
 * @author pidong
 * @date 2021/5/27 9:49
 */
@Getter
public enum RbacInnerUserType {

    MANAGER("0", "管理员"),
    NORMAL("1", "普通用户"),
    CAHS("2", "划款用户");

    private String value;
    private String desc;

    RbacInnerUserType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
