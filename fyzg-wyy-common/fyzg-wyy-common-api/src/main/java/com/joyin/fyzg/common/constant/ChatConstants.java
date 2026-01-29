package com.joyin.fyzg.common.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2023/9/15 18:23
 */
public class ChatConstants {


	public interface Destination {
		/** 角标刷新地址 */
		String BADGE_EXECUTE_URL = "/badgeExecute";
		/** 右下角通知地址 */
		String NOTIFICATION_EXECUTE_URL = "/notificationExecute";
	}

	public enum OpType {
		BADGE_INCREASE,
		BADGE_DECREASE,
		NOTIFICATION_TEXT,
		NOTIFICATION_HTML,
		;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	/**
	 * 角标刷新的长连接传输
	 * <br/>
	 * @author Administrator
	 * @date 2021/4/16 0016 下午 2:27
	 */
	public static class BadgeInfo {
		private String type;
		private OpType opType;
		private Object data;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	/**
	 * 右下角通知的长连接传输
	 * <br/>
	 * @author Administrator
	 * @date 2021/4/16 0016 下午 2:27
	 */
	public static class NotificationInfo {
		private OpType opType;
		private String title;
		private Object message;
	}
}
