package com.joyin.fyzg.wyy.common.exception;

import com.joyin.fyzg.common.ResponseCode;
import com.joyin.fyzg.common.WithCodeMsgFormatEnum;

/**
 * 功能列表解析器服务的消息定义
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/24 0024 上午 9:17
 */
public enum RbacExceptionEnum implements WithCodeMsgFormatEnum {
	MENU_MODULE_DELETE_EXIST_SUB_MODULE_FAIL(ResponseCode.WARNING.code, "删除失败，存在子模块！"),
	MENU_MODULE_DELETE_EXIST_SUB_PAGE_FAIL(ResponseCode.WARNING.code, "删除失败，存在子页面！"),

	//	=========
	MENU_PAGE_COPY_FAIL(ResponseCode.ERROR.code, "菜单复制失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单复制失败！";
		}
	},
	MENU_PAGE_COMPONENT_BATCH_SAVE_FAIL(ResponseCode.ERROR.code, "菜单组件批量保存失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单组件批量保存失败！";
		}
	},
	MENU_PAGE_ACTION_BATCH_SAVE_FAIL(ResponseCode.ERROR.code, "菜单的操作关联数据批量保存失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单的操作关联数据批量保存失败！";
		}
	},
	MENU_PAGE_REQUEST_BATCH_SAVE_FAIL(ResponseCode.ERROR.code, "菜单的请求关联数据批量保存失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单的请求关联数据批量保存失败！";
		}
	},
	MENU_PAGE_PARAMETER_BATCH_SAVE_FAIL(ResponseCode.ERROR.code, "菜单的参数关联数据批量保存失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单的参数关联数据批量保存失败！";
		}
	},
	ROLE_RELATE_BATCH_DELETE_4_MENU_FAIL(ResponseCode.ERROR.code, "菜单的关联数据批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单的关联数据批量删除失败！";
		}
	},
	ROLE_RELATE_BATCH_INSERT_4_MENU_FAIL(ResponseCode.ERROR.code, "菜单的关联数据批量插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单的关联数据批量插入失败！";
		}
	},
	ROLE_RELATE_BATCH_DELETE_4_MENU_ACTION_REQUEST_FAIL(ResponseCode.ERROR.code, "菜单的操作及请求关联数据批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单的操作及请求关联数据批量删除失败！";
		}
	},
	ROLE_RELATE_INSERT_4_PAGE_FAIL(ResponseCode.ERROR.code, "菜单及模块递归插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单及模块递归插入失败！";
		}
	},
	ROLE_RELATE_DELETE_4_PAGE_FAIL(ResponseCode.ERROR.code, "菜单的关联数据删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单的关联数据删除失败！";
		}
	},
    ROLE_RELATE_UPDATE_4_ROLE_TO_DATA_AU_FAIL(ResponseCode.ERROR.code, "角色关联数据权限更新失败！错误消息如下：【{0}】"){
        @Override
        public String getMsg() {
            return "角色关联数据权限更新失败！";
        }
    },
	ROLE_RELATE_BATCH_DELETE_4_PAGE_FAIL(ResponseCode.ERROR.code, "菜单的操作及请求关联数据批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单的操作及请求关联数据批量删除失败！";
		}
	},
	ROLE_RELATE_BATCH_SAVE_4_PAGE_FAIL(ResponseCode.ERROR.code, "菜单的操作及请求关联数据批量保存失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "菜单的操作及请求关联数据批量保存失败！";
		}
	},
	ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_USER_FAIL(ResponseCode.ERROR.code, "角色关联用户的数据批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "角色关联用户的数据批量删除失败！";
		}
	},
	ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_USER_FAIL(ResponseCode.ERROR.code, "角色关联用户的数据批量插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "角色关联用户的数据批量插入失败！";
		}
	},
	ROLE_RELATE_BATCH_DELETE_4_USER_TO_ROLE_FAIL(ResponseCode.ERROR.code, "用户关联角色的数据批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "用户关联角色的数据批量删除失败！";
		}
	},
	ROLE_RELATE_BATCH_INSERT_4_USER_TO_ROLE_FAIL(ResponseCode.ERROR.code, "用户关联角色的数据批量插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "用户关联角色的数据批量插入失败！";
		}
	},
	ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_WF_FAIL(ResponseCode.ERROR.code, "角色关联流程的数据批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "角色关联流程的数据批量删除失败！";
		}
	},
	ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_WF_FAIL(ResponseCode.ERROR.code, "角色关联流程的数据批量插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "角色关联流程的数据批量插入失败！";
		}
	},
	ROLE_RELATE_BATCH_DELETE_4_WF_TO_ROLE_FAIL(ResponseCode.ERROR.code, "流程关联角色的数据批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "流程关联角色的数据批量删除失败！";
		}
	},
	ROLE_RELATE_BATCH_INSERT_4_WF_TO_ROLE_FAIL(ResponseCode.ERROR.code, "流程关联角色的数据批量插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "流程关联角色的数据批量插入失败！";
		}
	},
	ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_DOC_FAIL(ResponseCode.ERROR.code, "角色关联文档的数据批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "角色关联文档的数据批量删除失败！";
		}
	},
	ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_DOC_FAIL(ResponseCode.ERROR.code, "角色关联文档的数据批量插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "角色关联文档的数据批量插入失败！";
		}
	},
	ROLE_RELATE_BATCH_DELETE_4_ROLE_TO_DESKTOP_FAIL(ResponseCode.ERROR.code, "角色关联桌面的数据批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "角色关联桌面的数据批量删除失败！";
		}
	},
	ROLE_RELATE_BATCH_INSERT_4_ROLE_TO_DESKTOP_FAIL(ResponseCode.ERROR.code, "角色关联桌面的数据批量插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "角色关联桌面的数据批量插入失败！";
		}
	},
	ROLE_RELATE_BATCH_INSERT_4_FIRST_WF_TO_ROLE_FAIL(ResponseCode.ERROR.code, "岗位关联流程第一个环节插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "岗位关联流程第一个环节插入失败！";
		}
	},
	USER_UPDATE_FAIL(ResponseCode.ERROR.code, "更新用户表时间失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "更新用户表数据失败！";
		}
	},
	;

	public final String code;
	public final String format;
	public String errorMsg;

	@Override
	public String getFormat() {
		return this.format;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMsg() {
		return this.errorMsg;
	}

	private RbacExceptionEnum(String code, String format) {
		this.code = code;
		this.format = format;
	}
}

