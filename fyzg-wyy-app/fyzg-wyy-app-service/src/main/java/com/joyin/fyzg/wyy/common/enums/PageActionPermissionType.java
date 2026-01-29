package com.joyin.fyzg.wyy.common.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * RoleRelateType
 * <br/>
 *
 * @author pengzhen
 * @date 2020/2/8 0008 下午 6:08
 */
@Getter
public enum PageActionPermissionType {
	E("E", "启用", 3),
	D("D", "禁用", 2),
	H("H", "隐藏", 1),
	;
	private String value;
	private String desc;
	private Integer level;

	PageActionPermissionType(final String value, final String desc, final Integer level) {
		this.value = value;
		this.desc = desc;
		this.level = level;
	}

	public static PageActionPermissionType getValue(String value) {
		return Arrays.stream(values()).filter(i -> i.getValue().equals(value)).findFirst().orElse(null);
	}
}
