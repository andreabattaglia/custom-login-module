/*
 *
 */
package org.jboss.eap.example.security.web.soap.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

public abstract class AbstractHTTPHeaderTest {
    public static final String USERNAME = "myuser";
    public static final String PASSWORD = "mypassword.01";
    public static final String TENANT_ID_KEY = "my.custom.header.tenantid";
    public static final String TENANT_ID = "tenantX";

    public static void setupHTTPHeaders(BindingProvider bp) {
        Map<String, Object> req_ctx = bp.getRequestContext();
        // Standard bindings
        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USERNAME);
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        // Custom Headers
        Map<String, List<String>> headers = new HashMap<>();
        headers.put(TENANT_ID_KEY, Collections.singletonList(TENANT_ID));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }
}
