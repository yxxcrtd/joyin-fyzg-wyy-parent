package com.joyin.fyzg.wyy.common.param;

import com.joyin.fyzg.common.RequestParamMap;
import com.joyin.fyzg.common.exception.CommonException;
import com.joyin.fyzg.utils.JsonUtils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

/**
 * 包装的参数Map
 * <br/>
 *
 * @author pengzhen
 * @date 2019/11/8 0008 上午 9:28
 */
public class GetDataParamMap extends RequestParamMap {

	public Long getRId() {
		return Long.valueOf(Optional.ofNullable(this.get(GetDataParamCode.rId))
				.orElseThrow(() -> new CommonException(MessageFormat.format("参数{0}缺失", GetDataParamCode.rId)))
				.toString()
		);
	}

	public String getOCode() {
		return Optional.ofNullable(this.get(GetDataParamCode.oCode))
				.orElseThrow(() -> new CommonException(MessageFormat.format("参数{0}缺失", GetDataParamCode.oCode)))
				.toString();
	}

	public Long getOType() {
		return Long.valueOf(Optional.ofNullable(this.get(GetDataParamCode.oType))
				.orElseThrow(() -> new CommonException(MessageFormat.format("参数{0}缺失", GetDataParamCode.oType)))
				.toString()
		);
	}

	public Long getDFlag() {
		return Long.valueOf(Optional.ofNullable(this.get(GetDataParamCode.dFlag))
				.orElseThrow(() -> new CommonException(MessageFormat.format("参数{0}缺失", GetDataParamCode.dFlag)))
				.toString()
		);
	}

	public Map<String, Object> getConditionMap() {
		return JsonUtils.json2map(Optional.ofNullable(this.get(GetDataParamCode.condition))
				.orElse("{}").toString());
	}

	public String getTabCode() {
		return Optional.ofNullable(this.get(GetDataParamCode.tabCode))
				.orElseThrow(() -> new CommonException(MessageFormat.format("参数{0}缺失", GetDataParamCode.tabCode)))
				.toString();
	}

	public String getDriverTabCode() {
		return Optional.ofNullable(this.get(GetDataParamCode.driverTabCode))
				.orElseThrow(() -> new CommonException(MessageFormat.format("参数{0}缺失", GetDataParamCode.driverTabCode)))
				.toString();
	}
}
