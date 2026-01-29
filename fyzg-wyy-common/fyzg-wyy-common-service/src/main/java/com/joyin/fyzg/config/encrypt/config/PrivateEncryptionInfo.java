package com.joyin.fyzg.config.encrypt.config;

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
@ConfigurationProperties(prefix = "joyin.encrypt.private")
public class PrivateEncryptionInfo {

	/**

	/**
	 * 需要对响应内容进行加密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> responseEncryptUriList;

	/**
	 * 需要对请求内容进行解密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> requestDecryptUriList;

	/**
	 * 忽略加密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> responseEncryptUriIgnoreList;


	/**
	 * 忽略对请求内容进行解密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> requestDecryptUriIgnoreList;


}
