//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.joyin.fyzg.wyy.config.cas.filter;

import org.jasig.cas.client.configuration.ConfigurationKeys;
import org.jasig.cas.client.session.SessionMappingStorage;
import org.jasig.cas.client.session.SingleSignOutHandler;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
@ConditionalOnProperty(name  = "cas.enabled", havingValue = "true")
public final class CasSingleSignOutFilter extends AbstractConfigurationFilter {
    private static final SingleSignOutHandler HANDLER = new SingleSignOutHandler();
    private AtomicBoolean handlerInitialized = new AtomicBoolean(false);

    public CasSingleSignOutFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        if (!this.isIgnoreInitConfiguration()) {
            this.setArtifactParameterName(this.getString(ConfigurationKeys.ARTIFACT_PARAMETER_NAME));
            this.setLogoutParameterName(this.getString(ConfigurationKeys.LOGOUT_PARAMETER_NAME));
            this.setRelayStateParameterName(this.getString(ConfigurationKeys.RELAY_STATE_PARAMETER_NAME));
            this.setCasServerUrlPrefix(this.getString(ConfigurationKeys.CAS_SERVER_URL_PREFIX));
            HANDLER.setArtifactParameterOverPost(this.getBoolean(ConfigurationKeys.ARTIFACT_PARAMETER_OVER_POST));
            HANDLER.setEagerlyCreateSessions(this.getBoolean(ConfigurationKeys.EAGERLY_CREATE_SESSIONS));
        }

        HANDLER.init();
        this.handlerInitialized.set(true);
    }

    public void setArtifactParameterName(String name) {
        HANDLER.setArtifactParameterName(name);
    }

    public void setLogoutParameterName(String name) {
        HANDLER.setLogoutParameterName(name);
    }

    public void setRelayStateParameterName(String name) {
        HANDLER.setRelayStateParameterName(name);
    }

    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        HANDLER.setCasServerUrlPrefix(casServerUrlPrefix);
    }

    public void setSessionMappingStorage(SessionMappingStorage storage) {
        HANDLER.setSessionMappingStorage(storage);
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if (!this.handlerInitialized.getAndSet(true)) {
            HANDLER.init();
        }

        if (HANDLER.process(request, response)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    public void destroy() {
    }

    protected static SingleSignOutHandler getSingleSignOutHandler() {
        return HANDLER;
    }
}
