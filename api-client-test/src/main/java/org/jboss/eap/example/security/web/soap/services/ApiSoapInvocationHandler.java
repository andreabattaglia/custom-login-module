package org.jboss.eap.example.security.web.soap.services;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ConnectException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ApiSoapInvocationHandler implements InvocationHandler {
    private static final Logger LOG = LoggerFactory
            .getLogger(ApiSoapInvocationHandler.class);
    private HelloServiceService HelloServiceService;
    private String username;
    private String password;
    private String loginTenant;

    public static HelloServiceService buildProxy(
            HelloServiceService helloServiceService, String[] authArgs) {
        return (HelloServiceService) Proxy.newProxyInstance(
                HelloServiceService.class.getClassLoader(),
                new Class[] { HelloServiceService.class },
                new ApiSoapInvocationHandler(helloServiceService, authArgs));
    }

    public static void sleep() {
        try {
            int retryTime = 5;
            Thread.sleep((int) (2 * retryTime * Math.random()));
        } catch (Exception e) {
            LOG.trace("sleep()", e);
        }
    }

    private ApiSoapInvocationHandler(HelloServiceService helloServiceService,
            String[] authArgs) {
        int i = 0;
        username = (String) authArgs[i++];
        password = (String) authArgs[i++];
        loginTenant = (String) authArgs[i++];
        this.HelloServiceService = helloServiceService;
        setupHTTPHeaders((BindingProvider) helloServiceService);
    }

    private void setupHTTPHeaders(BindingProvider bp) {
        Map<String, Object> req_ctx = bp.getRequestContext();
        // Standard bindings
        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
        // Custom Headers
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("my.custom.header.tenantid", Collections.singletonList(loginTenant));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        LOG.trace(
                "invoke(Object HelloServiceService={}, Method method={}, Object[] args={}) - start",
                HelloServiceService, method, args);

        String methodName = method.getName();
        if (methodName.startsWith("sendImmediateMessage")) {
            return method.invoke(HelloServiceService, args);
        }

        return callAuthenticatedMethod(method, args);
    }

    private Object callAuthenticatedMethod(Method method, Object[] args)
            throws Throwable {

        Throwable targetException = null;
        for (int i = 0; i < 100; i++) {
            try {
                return method.invoke(HelloServiceService, args);
            } catch (InvocationTargetException e) {
                targetException = e.getTargetException();
                Throwable targetCause = targetException.getCause();
                if (targetCause == null
                        || !(targetCause instanceof ConnectException)) {
                    throw targetException;
                }
                LOG.error("callAuthenticatedMethod(Object HelloServiceService="
                        + HelloServiceService + ", Method method=" + method
                        + ", Object[] args=" + args
                        + ") - exception ignored - HelloServiceService HelloServiceService="
                        + HelloServiceService + ", Object HelloServiceService="
                        + HelloServiceService + ", Method method=" + method
                        + ", Object[] args=" + args + ", Exception e="
                        + targetException);
            }
            sleep();
        }
        if (targetException != null) {
            throw targetException;
        }
        return null;
    }
}
