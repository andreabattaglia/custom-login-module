
/**
 *
 */
package org.jboss.eap.example.security.web.soap.services.hello;

import javax.xml.ws.BindingProvider;

import org.jboss.eap.example.security.web.soap.services.AbstractHTTPHeaderTest;
import org.jboss.eap.example.security.web.soap.services.HelloService;
import org.jboss.eap.example.security.web.soap.services.HelloServiceService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public class SecuredHelloServiceTest extends AbstractHTTPHeaderTest {
    private HelloService port;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        HelloServiceService hss = new HelloServiceService();
        port = hss.getHelloServicePort();
        setupHTTPHeaders((BindingProvider) port);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        port = null;
    }

    @Test
    public void testLogin() {
        String result = port.hello("Friend");
        System.out.println(result);
    }

}
