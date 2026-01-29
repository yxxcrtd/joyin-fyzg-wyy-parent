package com.joyin.fyzg.common;

/**
 * 用来规范枚举的接口，code和msg两个方法用来定义Response的结构（RestResponse和MethodResponse）
 * <br/>
 * @author pengzhen
 * @date 2019/7/22 0022 下午 4:46
 */
public interface WithCodeMsgEnum {

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

	/**
	 * 消息内容
	 * <br/>
	 * @param
	 * @return java.lang.String
	 * @author pengzhen
	 * @date 2019/7/23 0023 上午 11:27
	 */
	default String getErrorMsg(){
		return this.getMsg();
	}
}
