package com.joyin.fyzg.wyy.common.exception;

import com.joyin.fyzg.common.ResponseCode;
import com.joyin.fyzg.common.WithCodeMsgFormatEnum;

/**
 * 动态布局服务的消息定义
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/24 0024 上午 9:17
 */
public enum MldfExceptionEnum implements WithCodeMsgFormatEnum {
	APP_FORM_COL_RELATE_R1_NOT_CONFIG(ResponseCode.WARNING.code, "关联型指标【{0}】表单规则未配置！"),
	APP_FORM_COL_RELATE_R1_NOT_DEFINE(ResponseCode.WARNING.code, "关联型指标【{0}】表单规则配置了但未定义！"),
	APP_FORM_COL_RELATE_R2_NOT_CONFIG(ResponseCode.WARNING.code, "关联型指标【{0}】表格规则未配置！"),
	APP_FORM_COL_RELATE_R2_NOT_DEFINE(ResponseCode.WARNING.code, "关联型指标【{0}】表格规则配置了但未定义！"),
	APP_FORM_COL_OBJECT_O_MH_NOT_CONFIG(ResponseCode.WARNING.code, "对象型指标【{0}】模糊匹配展现形式的规则未配置！"),
	APP_FORM_COL_OBJECT_O_XL_NOT_CONFIG(ResponseCode.WARNING.code, "对象型指标【{0}】下拉列表展现形式的规则未配置！"),
	APP_FORM_COL_OBJECT_O_TC_NOT_CONFIG(ResponseCode.WARNING.code, "对象型指标【{0}】弹窗筛选展现形式的规则未配置！"),

	MODEL_OBJECT_TYPE_EXISTS_FAIL(ResponseCode.WARNING.code, "对象代码重复"),
	MODEL_OBJECT_NAME_EXISTS_FAIL(ResponseCode.WARNING.code, "对象名称重复"),
	MODEL_TABLE_NOT_DEFINE_FAIL(ResponseCode.WARNING.code, "表定义未创建"),

	MODEL_TABLE_PHYSICAL_EXISTS(ResponseCode.WARNING.code, "物理表名重复"),
	MODEL_TABLE_COMPOSITE_KEY_NOT_DEFINE(ResponseCode.WARNING.code, "联合主键字段未定义"),

	LAYOUT_RULE_LINKAGE_EXISTS_NAME(ResponseCode.WARNING.code, "当前联动规则已经存在"),

	APP_FORM_COL_QTY_NOT_DEFINE(ResponseCode.WARNING.code, "表单展示列数未配置！"),
	APP_FORM_COL_COLLAPSE_NOT_DEFINE(ResponseCode.WARNING.code, "指标分组规则未配置！"),
	APP_FORM_COL_COLLAPSE_NOT_EXISTS(ResponseCode.WARNING.code, "指标分组规则不存在！"),
	APP_RULE_NOT_DEFINE(ResponseCode.WARNING.code, "配置的布局ID未找到定义!"),
	APP_RULE_NOT_EXISTS(ResponseCode.WARNING.code, "配置未包含布局ID!"),
	APP_RULE_FORM_NOT_EXISTS(ResponseCode.WARNING.code, "表单模板未找到规则！"),
	APP_RULE_FORM_SIMPLE_NOT_EXISTS(ResponseCode.WARNING.code, "复合表单规则指向的简单表单模板未找到！"),
	APP_RULE_FORM_COMPOUND_NOT_EXISTS(ResponseCode.WARNING.code, "复合表单模板未找到规则！"),
	APP_RULE_TABLE_INLINE_NOT_EXISTS(ResponseCode.WARNING.code, "表格行内编辑模板未找到规则！"),
	APP_RULE_TABLE_DIALOG_NOT_EXISTS(ResponseCode.WARNING.code, "表格弹窗编辑模板未找到规则！"),
	APP_RULE_COUPLING_NOT_EXISTS(ResponseCode.WARNING.code, "松耦合页面未找到规则！"),


	APP_DEFINE_FAIL(ResponseCode.ERROR.code, "布局定义或配置出现错误！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "布局定义或配置出现错误！";
		}
	},

	LAYOUT_CORE_COMPOSE_COPY_FAIL(ResponseCode.ERROR.code, "布局排版信息复制失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "布局排版信息复制失败！";
		}
	},

	LAYOUT_RULE_OBJECT_SET_DEFAULT_FAIL(ResponseCode.ERROR.code, "对象指标默认规则插入失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "对象指标默认规则插入失败！";
		}
	},

	MODEL_COLUMN_BATCH_COPY_FAIL(ResponseCode.ERROR.code, "指标批量复制失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "指标批量复制失败！";
		}
	},

	MODEL_COLUMN_DELETE_FAIL(ResponseCode.ERROR.code, "指标删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "指标删除失败！";
		}
	},

	MODEL_COLUMN_PHYSICAL_CREATE_FAIL(ResponseCode.ERROR.code, "添加物理指标出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "添加物理指标出错！";
		}
	},
	MODEL_COLUMN_PHYSICAL_MODIFY_FAIL(ResponseCode.ERROR.code, "修改物理指标出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "修改物理指标出错！";
		}
	},
	MODEL_COLUMN_PHYSICAL_DROP_FAIL(ResponseCode.ERROR.code, "删除物理指标出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "删除物理指标出错！";
		}
	},


	MODEL_CONSTANT_ENUM_GROUP_BATCH_SAVE_FAIL(ResponseCode.ERROR.code, "分组枚举批量保存失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "分组枚举批量保存失败！";
		}
	},


	MODEL_OPTION_BATCH_DELETE_FAIL(ResponseCode.ERROR.code, "普通枚举明细批量删除失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "普通枚举明细批量删除失败！";
		}
	},

	MODEL_SUBJECT_BATCH_SAVE_FAIL(ResponseCode.ERROR.code, "对象主题分类批量保存失败！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "对象主题分类批量保存失败！";
		}
	},

	MODEL_TABLE_INCREMENT_FAIL(ResponseCode.ERROR.code, "构建表定义自增代码出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "构建表定义自增代码出错！";
		}
	},
	MODEL_TABLE_PHYSICAL_CREATE_FAIL(ResponseCode.ERROR.code, "创建物理表出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "创建物理表出错！";
		}
	},
	MODEL_PK_PHYSICAL_CREATE_FAIL(ResponseCode.ERROR.code, "创建主键约束出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "创建主键约束出错！";
		}
	},
	MODEL_R_UI_PHYSICAL_CREATE_FAIL(ResponseCode.ERROR.code, "创建R表唯一索引出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "创建R表唯一索引出错！";
		}
	},
	MODEL_D_NI_PHYSICAL_CREATE_FAIL(ResponseCode.ERROR.code, "创建D表非唯一索引出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "创建D表非唯一索引出错！";
		}
	},
	MODEL_D_FK_PHYSICAL_CREATE_FAIL(ResponseCode.ERROR.code, "创建D表外键出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "创建D表外键出错！";
		}
	},
	MODEL_D2_NI_PHYSICAL_CREATE_FAIL(ResponseCode.ERROR.code, "创建D2表非唯一索引出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "创建D2表非唯一索引出错！";
		}
	},
	MODEL_D2_FK_PHYSICAL_CREATE_FAIL(ResponseCode.ERROR.code, "创建D2表外键出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "创建D2表外键出错！";
		}
	},
	MODEL_SEQUENCE_PHYSICAL_CREATE_FAIL(ResponseCode.ERROR.code, "创建序列出错！错误消息如下：【{0}】"){
		@Override
		public String getMsg() {
			return "创建序列出错！";
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

	private MldfExceptionEnum(String code, String format) {
		this.code = code;
		this.format = format;
	}
}
