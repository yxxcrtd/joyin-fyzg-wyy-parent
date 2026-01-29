//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.joyin.fyzg.wyy.config.cas.filter;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.configuration.ConfigurationKeys;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;

@Component
@ConditionalOnProperty(name  = "cas.enabled", havingValue = "true")
public final class CasHttpServletRequestWrapperFilter extends AbstractConfigurationFilter {
    private String roleAttribute;
    private boolean ignoreCase;

    public CasHttpServletRequestWrapperFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        AttributePrincipal principal = this.retrievePrincipalFromSessionOrRequest(servletRequest);
        filterChain.doFilter(new CasHttpServletRequestWrapperFilter.CasHttpServletRequestWrapper((HttpServletRequest)servletRequest, principal), servletResponse);
    }

    protected AttributePrincipal retrievePrincipalFromSessionOrRequest(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpSession session = request.getSession(false);
        Assertion assertion = (Assertion)((Assertion)(session == null ? request.getAttribute("_const_cas_assertion_") : session.getAttribute("_const_cas_assertion_")));
        return assertion == null ? null : assertion.getPrincipal();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        this.roleAttribute = this.getString(ConfigurationKeys.ROLE_ATTRIBUTE);
        this.ignoreCase = this.getBoolean(ConfigurationKeys.IGNORE_CASE);
    }

    final class CasHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private final AttributePrincipal principal;

        CasHttpServletRequestWrapper(HttpServletRequest request, AttributePrincipal principal) {
            super(request);
            this.principal = principal;
        }

        public Principal getUserPrincipal() {
            return this.principal;
        }

        public String getRemoteUser() {
            return this.principal != null ? this.principal.getName() : null;
        }

        public boolean isUserInRole(String role) {
            if (CommonUtils.isBlank(role)) {
                CasHttpServletRequestWrapperFilter.this.logger.debug("No valid role provided.  Returning false.");
                return false;
            } else if (this.principal == null) {
                CasHttpServletRequestWrapperFilter.this.logger.debug("No Principal in Request.  Returning false.");
                return false;
            } else if (CommonUtils.isBlank(CasHttpServletRequestWrapperFilter.this.roleAttribute)) {
                CasHttpServletRequestWrapperFilter.this.logger.debug("No Role Attribute Configured. Returning false.");
                return false;
            } else {
                Object value = this.principal.getAttributes().get(CasHttpServletRequestWrapperFilter.this.roleAttribute);
                if (value instanceof Collection) {
                    Iterator var3 = ((Collection)value).iterator();

                    while(var3.hasNext()) {
                        Object o = var3.next();
                        if (this.rolesEqual(role, o)) {
                            CasHttpServletRequestWrapperFilter.this.logger.debug("User [{}] is in role [{}]: true", this.getRemoteUser(), role);
                            return true;
                        }
                    }
                }

                boolean isMember = this.rolesEqual(role, value);
                CasHttpServletRequestWrapperFilter.this.logger.debug("User [{}] is in role [{}]: {}", new Object[]{this.getRemoteUser(), role, isMember});
                return isMember;
            }
        }

        private boolean rolesEqual(String given, Object candidate) {
            return CasHttpServletRequestWrapperFilter.this.ignoreCase ? given.equalsIgnoreCase(candidate.toString()) : given.equals(candidate);
        }
    }
}
