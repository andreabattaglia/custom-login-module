package org.jboss.eap.example.security.web.soap.services;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.jboss.eap.example.security.ejb.services.impl.SupportServiceBean;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
@WebService
public class HelloService {

    @Inject
    private SupportServiceBean supportServiceBean;

    @WebMethod
    public String hello(@WebParam(name = "name") String name) {
        supportServiceBean.doNothing();
        return "Hello " + name + "!";
    }
}