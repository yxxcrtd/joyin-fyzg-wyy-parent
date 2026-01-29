package com.joyin.fyzg.wyy.config.cas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <br/>
 *
 * @author Administrator
 * @date 2023/10/31 11:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "cas")
public class CasInfo {
	private String serverUrlPrefix;
	private String serverLoginUrl;
	private String clientHostUrl;
	private String portalUrl;
	private String pattern301;
	private String route;
}
