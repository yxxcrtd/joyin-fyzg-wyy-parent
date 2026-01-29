package com.joyin.fyzg.common;

import java.util.HashMap;
import java.util.Optional;

/**
 * 包装的查询结果Map
 * <br/>
 *
 * @author pengzhen
 * @date 2019/11/8 0008 上午 9:28
 */
public class QueryResultMap extends HashMap {

	public String getOCode() {
		return Optional.ofNullable(this.get(SysConstant.TableField.oCode))
				.orElse("")
				.toString();
	}

	public String getOName() {
		return Optional.ofNullable(this.get(SysConstant.TableField.oName))
				.orElse("")
				.toString();
	}

}
