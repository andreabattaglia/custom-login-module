/*
 *
 */
package org.jboss.eap.example.security.loginmodule;

import java.security.acl.Group;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.security.auth.spi.UsersRolesLoginModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLoginModule extends UsersRolesLoginModule {
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CustomLoginModule.class);

    public static final String WEB_REQUEST_KEY = "javax.servlet.http.HttpServletRequest";
    public static final String CUSTOM_HEADERS_TENANT_ID = "my.custom.header.tenantid";

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler,
            Map<String, ?> sharedState, Map<String, ?> options) {
        super.initialize(subject, callbackHandler, sharedState, options);

    }

    @Override
    public boolean login() throws LoginException {
        super.login();
        if (!loginOk) {
            return false;
        }

        HttpServletRequest request = null;

        try {
            request = (HttpServletRequest) PolicyContext
                    .getContext(WEB_REQUEST_KEY);
        } catch (PolicyContextException e) {
            throw new LoginException(e.getLocalizedMessage());
        }
        if (LOGGER.isTraceEnabled()) {

            Map<String, String> map = new HashMap<String, String>();

            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }

            LOGGER.trace("Web request parameters: {}", map);
        }

        String tenantId = request.getHeader(CUSTOM_HEADERS_TENANT_ID);
        if ((null == tenantId) || "".equals(tenantId)) {
            return false;
        }
        CustomPrincipal principal = (CustomPrincipal) getIdentity();
        principal.setTenantId(tenantId.toString());
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        return super.logout();
    }

    @Override
    protected Group[] getRoleSets() throws LoginException {
        return super.getRoleSets();
    }
}