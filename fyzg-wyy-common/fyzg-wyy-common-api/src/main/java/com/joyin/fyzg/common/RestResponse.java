package com.joyin.fyzg.common;


import com.joyin.fyzg.common.constant.Constants;
import com.joyin.fyzg.common.utils.SwitchConfigUtils;
import org.slf4j.MDC;

/**
 * 前端返回的实体类
 * <br/>
 *
 * @author pengzhen
 * @date 2019/7/18 0018 下午 3:46
 */
public class RestResponse<T> {
	/**
	 * 返回的枚举
	 */
	private WithCodeMsgEnum withCodeMsgEnum;

	private T result;

	public String getCode() {
		return this.withCodeMsgEnum.getCode();
	}

	public String getTraceId() {
		return MDC.get(Constants.TRACE_ID);
	}

	public void setCode(String code) {
		if(ResponseCode.SUCESS.code.equals(code)){
			this.withCodeMsgEnum=new WithCodeMsgEnum() {
				@Override
				public String getCode() {
					return ResponseCode.SUCESS.code;
				}

				@Override
				public String getMsg() {
					return ResponseCode.SUCESS.code;
				}
			};
		}else{
			this.withCodeMsgEnum=new WithCodeMsgEnum() {
				@Override
				public String getCode() {
					return ResponseCode.FAIL.code;
				}

				@Override
				public String getMsg() {
					return ResponseCode.FAIL.code;
				}
			};
		}
	}

	public String getMsg() {
		return this.withCodeMsgEnum.getMsg();
	}

	public String getErrorMsg() {
		if (SwitchConfigUtils.isShowErrorMsg()){
			return this.withCodeMsgEnum.getErrorMsg();
		}else {
			return null;
		}
	}

	public T getResult() {
		return this.result;
	}

	/**
	 * 成功的返回结果（不带数据）
	 * <br/>
	 *
	 * @return com.joyin.fyzg.mapper.common.RestResponse<T>
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 5:02
	 */
	public static <K> RestResponse<K> success() {
		return success(null);
	}

	/**
	 * 成功的返回结果（带数据）
	 * <br/>
	 *
	 * @param result 要返回的数据
	 * @return com.joyin.fyzg.mapper.common.RestResponse<T>
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 5:01
	 */
	public static <K> RestResponse<K> success(K result) {
		return new RestResponse<K>(new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return ResponseCode.SUCESS.code;
			}

			@Override
			public String getMsg() {
				return ResponseCode.SUCESS.msg;
			}
		}, result);
	}

	/**
	 * 错误的返回（不带数据,code为默认的）
	 * <br/>
	 *
	 * @param msg 消息
	 * @return com.joyin.fyzg.mapper.common.RestResponse<K>
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 5:11
	 */
	public static <K> RestResponse<K> error(String msg) {
		return new RestResponse<K>(new WithCodeMsgEnum() {
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

	/**
	 * 错误的返回（不带数据,code为默认的）
	 * <br/>
	 *
	 * @param type 消息
	 * @return com.joyin.fyzg.mapper.common.RestResponse<K>
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 5:11
	 */
	public static <K> RestResponse<K> error(WithCodeMsgEnum type, K result) {
		return new RestResponse<K>(new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return type.getCode();
			}

			@Override
			public String getMsg() {
				return type.getMsg();
			}
		}, result);
	}

	/**
	 * 错误的返回（不带数据）
	 * <br/>
	 *
	 * @return com.joyin.fyzg.mapper.common.RestResponse<K>
	 * @author pengzhen
	 * @date 2019/7/15 0015 下午 5:11
	 */
	public static <K> RestResponse<K> error() {
		return new RestResponse<K>(new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return ResponseCode.FAIL.code;
			}

			@Override
			public String getMsg() {
				return ResponseCode.FAIL.msg;
			}
		});
	}

	/**
	 * 根据WithCodeMsgEnum组装RestResponse(带数据)
	 * <br/>
	 *
	 * @param withCodeMsgEnum
	 * @return com.joyin.fyzg.mapper.common.MethodResponse
	 * @author pengzhen
	 * @date 2019/7/22 0022 下午 3:57
	 */
	public static RestResponse transWithCodeMsgEnum(WithCodeMsgEnum withCodeMsgEnum) {
		return new RestResponse(withCodeMsgEnum);
	}

	public static <K> RestResponse<K> transMethodResponse(MethodResponse<K> methodResponse) {
		return new RestResponse<K>(methodResponse.getWithCodeMsgEnum(), methodResponse.getData());
	}

	public static <K> RestResponse<K> transFeignResponse(FeignResponse<K> feignResponse) {
		WithCodeMsgEnum codeMsgEnum = new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return feignResponse.getCode();
			}

			@Override
			public String getMsg() {
				return feignResponse.getMsg();
			}
		};
		return new RestResponse<K>(codeMsgEnum, feignResponse.getResult());
	}

	public static <K> RestResponse<K> transFeignSuccess(FeignResponse<K> feignResponse) {
		return new RestResponse<K>(new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return ResponseCode.SUCESS.code;
			}

			@Override
			public String getMsg() {
				return ResponseCode.SUCESS.msg;
			}
		}, feignResponse.getResult());
	}

	public RestResponse() {
	}

	private RestResponse(WithCodeMsgEnum withCodeMsgEnum) {
		this(withCodeMsgEnum,null);
	}

	private RestResponse(WithCodeMsgEnum withCodeMsgEnum, T result) {
		this.withCodeMsgEnum = withCodeMsgEnum;
		this.result = result;
	}
}
