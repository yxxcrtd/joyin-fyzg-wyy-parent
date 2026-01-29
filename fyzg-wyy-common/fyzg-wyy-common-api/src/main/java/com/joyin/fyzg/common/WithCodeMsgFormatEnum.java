package com.joyin.fyzg.common;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * 相比WithCodeMsgEnum多了格式化的功能
 * <br/>
 * @author pengzhen
 * @date 2020/6/21 0021 上午 10:30
 */
public interface WithCodeMsgFormatEnum {

	/**
	 * 消息代码
	 * <br/>
	 * @param
	 * @return java.lang.String
	 * @author pengzhen
	 * @date 2019/7/23 0023 上午 11:27
	 */
	String getCode();

	/**
	 * 消息内容
	 * <br/>
	 * @param
	 * @return java.lang.String
	 * @author pengzhen
	 * @date 2019/7/23 0023 上午 11:27
	 */
	String getMsg();

	String getFormat();

	default WithCodeMsgEnum format(Object... arguments){
		WithCodeMsgFormatEnum that=this;
		return new WithCodeMsgEnum() {
			@Override
			public String getCode() {
				return that.getCode();
			}

			@Override
            public String getMsg() {
				return Optional.ofNullable(that.getMsg()).map(msg->MessageFormat.format(msg, arguments)).orElse(this.getErrorMsg());
			}

			@Override
			public String getErrorMsg() {
				return MessageFormat.format(that.getFormat(), arguments);
			}
		};
	}
}
