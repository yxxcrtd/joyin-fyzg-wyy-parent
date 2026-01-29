package com.joyin.fyzg.wyy.vo.rbac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class UserFinancierVO {


	/** 机构代码 */
	private String financierCode;


	/** 机构名称 */
	private String financierName;


	/** 是否选中 */
	private Boolean selected;

	public UserFinancierVO(String financierCode, String financierName, Boolean selected) {
		this.financierCode = financierCode;
		this.financierName = financierName;
		this.selected = selected;
	}
}
