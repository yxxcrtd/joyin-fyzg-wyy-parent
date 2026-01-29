package com.joyin.fyzg.config.encrypt.config;

import com.joyin.fyzg.config.encrypt.core.ApiEncryptDataInit;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 加解密配置类
 *
 * @author yinjihuan
 */
@Data
@Component
@ConfigurationProperties(prefix = "joyin.encrypt.public")
public class PublicEncryptionInfo {

	/**
	 * AES加密Key
	 */
	private String key = "d86d7bab3d6ac01ad9dc6a897652f2d2";

	/**
	 * 响应数据编码
	 */
	private String responseCharset = "UTF-8";

	/**
	 * 加解密开关，true为打开，会进行加解密，false反之
	 */
	private boolean encryptSwitch = false;

	/**
	 * 过滤器拦截模式
	 */
	private String[] urlPatterns = new String[] { "/*" };

	/**
	 * 过滤器执行顺序
	 */
	private int order = 1;

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
	 * 忽略加密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> privateResponseEncryptUriIgnoreList;

	/**
	 * 忽略对请求内容进行解密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> requestDecryptUriIgnoreList;

	/**
	 * 忽略对请求内容进行解密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> privateRequestDecryptUriIgnoreList;

	/**
	 * 表单提交的请求返回值是否需要加密
	 */
	private boolean formEncrypt = true;

	public List<String> getRequestDecryptParams(String uri) {
		List<String> params = ApiEncryptDataInit.requestDecryptParamMap.get(uri);
		if (CollectionUtils.isEmpty(params)) {
			return new ArrayList<>();
		}

		return params;
	}

}
