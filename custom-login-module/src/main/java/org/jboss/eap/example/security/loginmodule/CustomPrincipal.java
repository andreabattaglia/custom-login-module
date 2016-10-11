/**
 *
 */
package org.jboss.eap.example.security.loginmodule;

import java.security.Principal;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public class CustomPrincipal implements Principal {

    private final String name;
    private String tenantId;

    public CustomPrincipal(String name) {
        this.name = name;
    }

    /**
     * @see java.security.Principal#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        result = (prime * result)
                + ((tenantId == null) ? 0 : tenantId.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CustomPrincipal other = (CustomPrincipal) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (tenantId == null) {
            if (other.tenantId != null) {
                return false;
            }
        } else if (!tenantId.equals(other.tenantId)) {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MyPrincipal [\n    name=");
        builder.append(name);
        builder.append(", \n    tenantId=");
        builder.append(tenantId);
        builder.append("\n]");
        return builder.toString();
    }

}
