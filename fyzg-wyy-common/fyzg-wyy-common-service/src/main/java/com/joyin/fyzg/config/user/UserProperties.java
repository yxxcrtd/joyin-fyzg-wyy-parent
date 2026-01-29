package com.joyin.fyzg.config.user;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <br/>
 *
 * @author jinyuan.lin
 * @date 2023/10/23 16:09
 */
@Component
@ConfigurationProperties(prefix = "user")
@Data
public class UserProperties {

	/* 默认密码 */
	private String pwdDefault ;
	/* 密码字段 */
	private String pwdField  ;
	/* 管理员账户账号 */
	private String adminAccount  ;
	/* 经过多少个月过期 */
	private Integer expirationMonth  ;
	/* 是否强制修改 */
	private boolean forcedModify ;
	/* 到期提前提醒天 */
	private Integer expirationRemindDays ;
	/* 密码锁定时间 */
	private Integer lockMinute ;
	/* 密码错误次数 */
	private Integer passErrorNum ;
	/* 存在登录用户是否踢出其他用户 */
	private boolean singleSignOn;

}
