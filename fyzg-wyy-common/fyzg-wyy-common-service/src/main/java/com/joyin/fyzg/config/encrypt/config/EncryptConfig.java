package com.joyin.fyzg.config.encrypt.config;

import com.joyin.fyzg.config.encrypt.core.ApiEncryptDataInit;
import com.joyin.fyzg.config.encrypt.core.EncryptionFilter;
import com.joyin.fyzg.config.encrypt.interceptor.FeignEncryptRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author cqh
 * @date 2021/10/8 2:39 下午
 */
//@ConditionalOnExpression("!'fyzg-api'.equals('${spring.application.name}')")
@Configuration
public class EncryptConfig  {

    @Autowired
    EncryptionFilter encryptionFilter;

    /**
     * 自定义Feign拦截器
     * @return
     */
    @Bean
    public FeignEncryptRequestInterceptor feignEncryptRequestInterceptor(){
        return new FeignEncryptRequestInterceptor();
    }

    @Bean
    public FilterRegistrationBean encryptionFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(encryptionFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
        registrationBean.setName("EncryptionFilter");
        return registrationBean;
    }

    @Bean
    public ApiEncryptDataInit apiEncryptDataInit() {
        return new ApiEncryptDataInit();
    }

}
