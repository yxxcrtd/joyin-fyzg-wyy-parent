package com.joyin.fyzg.wyy.page.user;

/**
 * <br/>
 *
 * @author jinyuan.lin
 * @date 2023/10/25 13:44
 */
public class UserMqConstant {

	/**延迟队列名*/
	public static final String DELAY_QUEUE = "userLocked.queue.delay";
	/**延迟队列(死信队列)交换器名*/
	public static final String DELAY_EXCHANGE = "userLocked.exchange";
	/**处理业务的队列(死信队列)*/
	public static final String PROCESS_QUEUE = "userLocked.queue.process";

}
