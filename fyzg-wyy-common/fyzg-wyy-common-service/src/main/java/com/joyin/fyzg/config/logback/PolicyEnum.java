package com.joyin.fyzg.config.logback;

import lombok.Getter;

/**
 * 日志策略
 * <br/>
 *
 * @author pidong
 * @date 2022/3/10 17:03
 */
@Getter
public enum PolicyEnum {
    replace("replace"),
    drop("drop"),
    erase("erase");

    public String code;

    PolicyEnum(String code) {
        this.code = code;
    }

    public static String codeOf(String str){
        for (PolicyEnum policy : values()) {
            if (policy.getCode().equals(str)) {
                return str;
            }
        }
        return null;
    }
}
