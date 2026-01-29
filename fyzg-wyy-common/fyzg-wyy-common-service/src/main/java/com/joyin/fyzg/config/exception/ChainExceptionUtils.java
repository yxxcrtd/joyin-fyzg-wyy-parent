package com.joyin.fyzg.config.exception;

import com.joyin.fyzg.common.FeignResponse;
import com.joyin.fyzg.common.WithCodeMsgEnum;
import com.joyin.fyzg.common.exception.WithTypeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChainExceptionUtils {

	public static <T> T throwFeignResponseExceptionWithReturn(FeignResponse feignResponse) {
		throwException(new WithCodeMsgEnum() {
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
		}, null);
		return null;
	}

	/**
	 * 抛出指定类型及指定枚举的异常
	 * <br/>
	 *
	 * @param type
	 * @param e
	 * @return void
	 * @author Administrator
	 * @date 2020/10/26 0026 上午 11:58
	 */
	public static <T> T throwExceptionWithReturn(WithCodeMsgEnum type, Throwable e) {
		throwException(type, e);
		return null;
	}

	/**
	 * 抛出指定类型及指定枚举的异常
	 * <br/>
	 *
	 * @param type
	 * @param e
	 * @return void
	 * @author Administrator
	 * @date 2020/10/26 0026 上午 11:58
	 */
	private static void throwException(WithCodeMsgEnum type, Throwable e) {
		if (e instanceof WithTypeException) {
			throw (RuntimeException) e;
		}
		else {
			throw new WithTypeException(type, e);
		}
	}
}
