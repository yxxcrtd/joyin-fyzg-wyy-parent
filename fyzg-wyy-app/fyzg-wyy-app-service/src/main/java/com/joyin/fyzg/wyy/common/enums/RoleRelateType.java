package com.joyin.fyzg.wyy.common.enums;

import lombok.Getter;

/**
 * RoleRelateType
 * <br/>
 *
 * @author pengzhen
 * @date 2020/2/8 0008 下午 6:08
 */
@Getter
public enum RoleRelateType {
	NULL("NULL", "空值占位"),
	USER("USER", "角色关联用户"),
	MENU_MODULE("MENU_MODULE", "角色关联菜单模块"),
	MENU_PAGE("MENU_PAGE", "角色关联菜单页面"),
	MENU_PAGE$ACTION("MENU_PAGE$ACTION", "角色关联菜单页面及动作"),
	MENU_PAGE$REQUEST("MENU_PAGE$REQUEST", "角色关联菜单页面及请求"),
	WF("WF", "角色关联菜流程环节"),
	CA("CA", "角色关联文档目录"),
	;
	private String value;
	private String desc;

	RoleRelateType(final String value, final String desc) {
		this.value = value;
		this.desc = desc;
	}
}
