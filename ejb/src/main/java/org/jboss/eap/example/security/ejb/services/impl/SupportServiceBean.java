package org.jboss.eap.example.security.ejb.services.impl;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.security.annotation.SecurityDomain;

@Stateless
@LocalBean
@RolesAllowed({ "superuser" })
@SecurityDomain("myloginmodule")
public class SupportServiceBean {

    public void doNothing() {

    }
}
