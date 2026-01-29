package com.joyin.fyzg.config.encrypt.core;

import com.google.common.collect.Lists;
import com.joyin.fyzg.config.encrypt.annotation.Decrypt;
import com.joyin.fyzg.config.encrypt.annotation.DecryptIgnore;
import com.joyin.fyzg.config.encrypt.annotation.Encrypt;
import com.joyin.fyzg.config.encrypt.annotation.EncryptIgnore;
import com.joyin.fyzg.config.encrypt.config.PrivateEncryptionInfo;
import com.joyin.fyzg.config.encrypt.config.PublicEncryptionInfo;
import com.joyin.fyzg.config.encrypt.utils.RequestUriUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;

public class ApiEncryptDataInit implements ApplicationContextAware {

	private Logger logger = LoggerFactory.getLogger(ApiEncryptDataInit.class);

	/**
	 * 需要对响应内容进行加密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	public static List<String> responseEncryptUriList = new ArrayList<>();

	/**
	 * 需要对请求内容进行解密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	public static List<String> requestDecryptUriList = new ArrayList<>();

	/**
	 * 忽略加密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	public static List<String> responseEncryptUriIgnoreList = new ArrayList<>();

	/**
	 * 忽略对请求内容进行解密的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	public static List<String> requestDecryptUriIgnoreList = new ArrayList<String>();

	/**
	 * Url参数需要解密的配置
	 * 比如：/user/list?name=加密内容<br>
	 * 格式：Key API路径  Value 需要解密的字段
	 * 示列：/user/list  [name,age]
	 */
	public static Map<String, List<String>> requestDecryptParamMap = new HashMap<>();

	private String contextPath;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.contextPath = ctx.getEnvironment().getProperty("server.servlet.context-path");
		Map<String, Object> beanMap = ctx.getBeansWithAnnotation(Controller.class);
		PublicEncryptionInfo publicEncryptionInfo = ctx.getBean(PublicEncryptionInfo.class);
		PrivateEncryptionInfo privateEncryptionInfo = ctx.getBean(PrivateEncryptionInfo.class);
		BiConsumer<List<String>,List<String>> mergeConsumer = (List<String> t,List<String> s)->{
			t.addAll(Optional.ofNullable(s).orElse(Lists.newArrayList()));
		};
		// 配置和注解两种方式合并
		mergeConsumer.accept(responseEncryptUriList, publicEncryptionInfo.getResponseEncryptUriList());
		mergeConsumer.accept(requestDecryptUriList, publicEncryptionInfo.getRequestDecryptUriList());
		mergeConsumer.accept(responseEncryptUriIgnoreList, publicEncryptionInfo.getResponseEncryptUriIgnoreList());
		mergeConsumer.accept(requestDecryptUriIgnoreList, publicEncryptionInfo.getRequestDecryptUriIgnoreList());

		mergeConsumer.accept(responseEncryptUriList, privateEncryptionInfo.getResponseEncryptUriList());
		mergeConsumer.accept(requestDecryptUriList, privateEncryptionInfo.getRequestDecryptUriList());
		mergeConsumer.accept(responseEncryptUriIgnoreList, privateEncryptionInfo.getResponseEncryptUriIgnoreList());
		mergeConsumer.accept(requestDecryptUriIgnoreList, privateEncryptionInfo.getRequestDecryptUriIgnoreList());
		initData(beanMap);
		initRequestDecyptParam(ctx.getEnvironment());
	}

	/**
	 * 初始化Url 参数解密配置
	 *
	 * @param environment
	 */
	private void initRequestDecyptParam(Environment environment) {
		for (Iterator it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext(); ) {
			PropertySource propertySource = (PropertySource) it.next();
			if (propertySource instanceof EnumerablePropertySource) {
				for (String name : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
					if (name.startsWith("spring.encrypt.requestDecyptParam")) {
						String[] keys = name.split("\\.");
						String key = keys[keys.length - 1];
						String property = environment.getProperty(name);
						requestDecryptParamMap.put(key.replace("$", ":"), Arrays.asList(property.split(",")));
					}
				}
			}
		}
	}

	private void initData(Map<String, Object> beanMap) {
		if (beanMap != null) {
			for (Object bean : beanMap.values()) {
				Class<?> clz = bean.getClass();
				Method[] methods = clz.getMethods();
				for (Method method : methods) {
					Encrypt encrypt = AnnotationUtils.findAnnotation(method, Encrypt.class);
					if (encrypt != null) {
						// 注解中的URI优先级高
						String uri = encrypt.value();
						if (!StringUtils.hasText(uri)) {
							responseEncryptUriList.addAll(RequestUriUtils.getApiUri(clz, method, contextPath));
						}
						logger.debug("Encrypt URI: {}", uri);
						if (StringUtils.hasText(uri)) {
							responseEncryptUriList.add(uri);
						}
					}
					Decrypt decrypt = AnnotationUtils.findAnnotation(method, Decrypt.class);
					if (decrypt != null) {
						String uri = decrypt.value();
						if (!StringUtils.hasText(uri)) {
							requestDecryptUriList.addAll(RequestUriUtils.getApiUri(clz, method, contextPath));
						}

						String decyptParam = decrypt.decyptParam();
						if (StringUtils.hasText(decyptParam)) {
							requestDecryptParamMap.put(uri, Arrays.asList(decyptParam.split(",")));
						}

						logger.debug("Decrypt URI: {}", uri);
						if (StringUtils.hasText(uri)) {
							requestDecryptUriList.add(uri);
						}
					}
					EncryptIgnore encryptIgnore = AnnotationUtils.findAnnotation(method, EncryptIgnore.class);
					if (encryptIgnore != null) {
						// 注解中的URI优先级高
						String uri = encryptIgnore.value();
						if (!StringUtils.hasText(uri)) {
							responseEncryptUriIgnoreList.addAll(RequestUriUtils.getApiUri(clz, method, contextPath));
						}
						logger.debug("EncryptIgnore URI: {}", uri);
						if (StringUtils.hasText(uri)) {
							responseEncryptUriIgnoreList.add(uri);
						}
					}
					DecryptIgnore decryptIgnore = AnnotationUtils.findAnnotation(method, DecryptIgnore.class);
					if (decryptIgnore != null) {
						String uri = decryptIgnore.value();
						if (!StringUtils.hasText(uri)) {
							requestDecryptUriIgnoreList.addAll(RequestUriUtils.getApiUri(clz, method, contextPath));
						}
						logger.debug("DecryptIgnore URI: {}", uri);
						if (StringUtils.hasText(uri)) {
							requestDecryptUriIgnoreList.add(uri);
						}
					}
				}
			}
		}
	}

}
