package org.jboss.eap.example.security.ejb.services.impl;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
@Stateless
@SecurityDomain("myloginmodule")
@RolesAllowed("akdbsuperuser")
@WebService
public class HelloServiceBean {
    
    @EJB
    private SupportServiceBean supportServiceBean;
    
    public String hello(@WebParam(name = "name") String name) {
        supportServiceBean.doNothing();
        return "Hello " + name + "!";
    }
}