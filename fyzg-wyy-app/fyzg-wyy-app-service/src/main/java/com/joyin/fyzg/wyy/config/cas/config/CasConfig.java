package com.joyin.fyzg.wyy.config.cas.config;

import com.joyin.fyzg.wyy.config.cas.filter.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name  = "cas.enabled", havingValue = "true")
public class CasConfig {

	private final CasInfo casInfo;
	private final CasSingleSignOutFilter casSingleSignOutFilter;
	private final CasAuthenticationFilter casAuthenticationFilter;
	private final Cas300ProxyReceivingTicketValidationFilter cas300ProxyReceivingTicketValidationFilter;
	private final CasHttpServletRequestWrapperFilter casHttpServletRequestWrapperFilter;
	private final MyUserFilter myUserFilter;

	@Bean
	public ServletListenerRegistrationBean servletListenerRegistrationBean() {
		ServletListenerRegistrationBean listenerRegistrationBean = new ServletListenerRegistrationBean();
		listenerRegistrationBean.setListener(new SingleSignOutHttpSessionListener());
		listenerRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return listenerRegistrationBean;
	}

	/**
	 * 自定义的单点登录退出
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean singleSignOutFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(casSingleSignOutFilter);
		registrationBean.addInitParameter("casServerUrlPrefix", casInfo.getServerUrlPrefix());
		registrationBean.setName("singleSignOutFilter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(2);
		return registrationBean;
	}

	/**
	 * 自定义的单点登录校验
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean cas30ProxyReceivingTicketValidationFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(cas300ProxyReceivingTicketValidationFilter);
		registrationBean.addInitParameter("casServerUrlPrefix", casInfo.getServerUrlPrefix());
		registrationBean.addInitParameter("serverName", casInfo.getClientHostUrl());
		registrationBean.setName("cas30ProxyReceivingTicketValidationFilter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(3);
		return registrationBean;
	}

	/**
	 * 自定义的单点登录请求包装
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean httpServletRequestWrapperFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(casHttpServletRequestWrapperFilter);
		registrationBean.setName("httpServletRequestWrapperFilter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(4);
		return registrationBean;
	}

	/**
	 * 自定义的单点登录认证（如果未登录会经过这个Filter重定向回服务端获取ST）
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean authenticationFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		casAuthenticationFilter.setPattern301(casInfo.getPattern301());
		registrationBean.setFilter(casAuthenticationFilter);
		registrationBean.addInitParameter("casServerLoginUrl", casInfo.getServerLoginUrl());
		registrationBean.addInitParameter("serverName", casInfo.getClientHostUrl());
		registrationBean.setName("authenticationFilter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(5);
		return registrationBean;
	}

	/**
	 * 自定义的过滤器
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean userFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(myUserFilter);
		registrationBean.setName("userFilter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(6);
		return registrationBean;
	}
}
