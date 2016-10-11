/*
 *
 */
package org.jboss.eap.example.security.cdi;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.enterprise.context.RequestScoped;
import javax.xml.ws.WebServiceContext;

import org.jboss.eap.example.security.loginmodule.CustomPrincipal;

@RequestScoped
public class PrincipalInfoService {

    @Resource
    private SessionContext sessionContext;
    @Resource
    private WebServiceContext webServiceContext;

    private CustomPrincipal principal;

    @PostConstruct
    private void init() {
        principal = (CustomPrincipal) sessionContext.getCallerPrincipal();
    }

    /**
     * @return
     * @see org.jboss.eap.example.security.loginmodule.CustomPrincipal#getName()
     */
    public String getUsername() {
        return principal.getName();
    }

    /**
     * @return
     * @see org.jboss.eap.example.security.loginmodule.CustomPrincipal#getTenantId()
     */
    public String getTenantId() {
        return principal.getTenantId();
    }

}
