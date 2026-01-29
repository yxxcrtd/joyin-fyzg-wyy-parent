package com.joyin.fyzg.common;

public enum ResponseCode {

	SUCESS("100000", "成功"),
	FAIL("100001", "失败"),
	INFO("100002", "提示"),
	WARNING("100003", "警告"),
	ERROR("100004", "错误"),

	//	错误码格式说明
	// 	分成两种：系统级错误，服务级错误；
	//	系统级错误：1开头，6位纯数字
	// 服务级错误：大写L字母、数字、中划线组成的8位字符串。前4位为服务名简称，不够4位右侧用“-”补齐，中间两位纯数字，末尾两位纯数字..
	// （示例：ML--0101）：
	//	ML--		01				01
	//	服务名简称	模块代码	具体错误代码
	//  系统级错误
	//	服务级错误
	AUTH_ERROR("AUTH", "动态布局异常"),
	MLDF_ERROR("ML--", "动态布局异常"),
	FLDF_ERROR("FL--", "功能列表异常"),
	RPTDF_ERROR("RPT-", "报表单据异常"),
	RISKDF_ERROR("RISK-", "风险预警异常"),
	RE_AUTH_ERROR("100005", "token过期");

	public final String code;
	public final String msg;

	private ResponseCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
