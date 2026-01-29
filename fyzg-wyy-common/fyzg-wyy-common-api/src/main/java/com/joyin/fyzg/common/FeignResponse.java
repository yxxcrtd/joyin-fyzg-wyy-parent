package com.joyin.fyzg.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 前端返回的实体类
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/18 0018 下午 3:46
 */
@Data
public class FeignResponse<T> {

	private String code;

	private String msg;

	private String errorMsg;

	private T result;

	public static <U> FeignResponse<U> buildError() {
		FeignResponse obj = new FeignResponse<U>();
		obj.setCode(ResponseCode.FAIL.toString());
		return obj;
	}

	/**
	 * 判断是否为成功状态
	 * <br/>
	 *
	 * @param
	 * @return boolean
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 5:00
	 */
	@JsonIgnore
	public boolean isSuccess() {
		return ResponseCode.SUCESS.code.equals(this.getCode());
	}

	/**
	 * 判断是否为失败状态
	 * <br/>
	 *
	 * @param
	 * @return boolean
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 5:00
	 */
	@JsonIgnore
	public boolean isError() {
		return !this.isSuccess();
	}
}
