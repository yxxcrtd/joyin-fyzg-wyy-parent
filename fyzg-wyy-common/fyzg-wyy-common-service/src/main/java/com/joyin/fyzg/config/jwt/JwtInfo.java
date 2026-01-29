package com.joyin.fyzg.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 加解密配置类
 *
 * @author yinjihuan
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtInfo {

	private String secret;

	private String header;

	private Long expiration;

	private Long paragraph=2L;

	private List<String> uriIgnoreList;

}
