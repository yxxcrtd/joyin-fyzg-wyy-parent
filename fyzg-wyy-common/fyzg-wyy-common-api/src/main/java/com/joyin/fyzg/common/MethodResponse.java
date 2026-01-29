package com.joyin.fyzg.common;

import lombok.Data;

/**
 * 业务层接口返回的实体类
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/18 0018 下午 3:47
 */
@Data
public class MethodResponse<T> {

	/**
	 * service返回的枚举
	 */
	private WithCodeMsgEnum withCodeMsgEnum;
	/**
	 * 返回给前台的数据
	 */
	private T data;

	/**
	 * 判断是否为成功状态
	 * <br/>
	 *
	 * @param
	 * @return boolean
	 * @author pengzhen
	 * @date 2019/7/23 0023 上午 9:57
	 */
	public boolean isSuccess() {
		return this.withCodeMsgEnum.getCode().equals(ResponseCode.SUCESS.code);
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
	public boolean isError() {
		return !this.isSuccess();
	}

	/**
	 * 成功的返回结果（带数据）
	 * <br/>
	 *
	 * @param data 要返回的数据
	 * @return com.joyin.fyzg.mapper.common.MethodResponse
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 4:57
	 */
	public static <K> MethodResponse<K> success(K data) {
		return new MethodResponse<K>(new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return ResponseCode.SUCESS.code;
			}

			@Override
			public String getMsg() {
				return ResponseCode.SUCESS.msg;
			}
		},data);
	}

	/**
	 * 成功的返回结果
	 * <br/>
	 *
	 * @param
	 * @return com.joyin.fyzg.mapper.common.MethodResponse
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 4:56
	 */
	public static MethodResponse success() {
		return success(null);
	}

	/**
	 * 错误的返回结果(不带数据)
	 * <br/>
	 *
	 * @param withCodeMsgEnum  消息
	 * @param data
	 * @return com.joyin.fyzg.mapper.common.MethodResponse
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 4:57
	 */
	public static <K> MethodResponse<K> error(WithCodeMsgEnum withCodeMsgEnum, K data) {
		return new MethodResponse(new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return withCodeMsgEnum.getCode();
			}

			@Override
			public String getMsg() {
				return withCodeMsgEnum.getMsg();
			}
		}, data);
	}

	/**
	 * 错误的返回结果(不带数据)
	 * <br/>
	 *
	 * @param msg  消息
	 * @return com.joyin.fyzg.mapper.common.MethodResponse
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 4:57
	 */
	public static MethodResponse error(String msg) {
		return new MethodResponse(new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return ResponseCode.FAIL.code;
			}

			@Override
			public String getMsg() {
				return msg;
			}
		});
	}

	public static MethodResponse transErrorMethodResponse(FeignResponse feignResponse) {
		return new MethodResponse(new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return feignResponse.getCode();
			}

			@Override
			public String getMsg() {
				return feignResponse.getMsg();
			}

			@Override
			public String getErrorMsg() {
				return feignResponse.getErrorMsg();
			}
		},feignResponse.getResult());
	}

	public static MethodResponse transErrorMethodResponse(MethodResponse methodResponse) {
		return new MethodResponse(methodResponse.getWithCodeMsgEnum(),null);
	}

	public static MethodResponse transWithCodeMsgEnum(WithCodeMsgEnum withCodeMsgEnum) {
		return new MethodResponse(withCodeMsgEnum);
	}

	public static MethodResponse transWithCodeMsgFormatEnum(WithCodeMsgFormatEnum withCodeMsgFormatEnum, Object... arguments) {
		return new MethodResponse(withCodeMsgFormatEnum.format(arguments));
	}

	private MethodResponse() {
	}

	private MethodResponse(WithCodeMsgEnum withCodeMsgEnum, T data) {
		this.withCodeMsgEnum = withCodeMsgEnum;
		this.data = data;
	}

	private MethodResponse(WithCodeMsgEnum withCodeMsgEnum) {
		this(withCodeMsgEnum,null);
	}
}
