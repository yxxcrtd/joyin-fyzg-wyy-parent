package com.joyin.fyzg.vo.enums;

import lombok.Getter;

/**
 * FlagType
 * <br/>
 *
 * @author pengzhen
 * @date 2019/8/27 0027 上午 9:34
 */
@Getter
public enum FlagType {
	YES("1", "是"),
	NO("0", "否");

	private String value;
	private String desc;

	FlagType(final String value, final String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static FlagType getEnumFromString(String string) {
		for (FlagType color : values()) {
			if (color.getValue().equals(string)) {
				return color;
			}
		}
		return null;
	}
}
