package com.labcenter.security;

import java.security.Principal;
import java.util.List;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

public class IdentitySecurityContext implements SecurityContext {
    @Context
    private ContainerRequestContext requestContext;
    private Principal principal;
    private List<String> roles;

    public IdentitySecurityContext(Principal principal, List<String> roles) {
        this.principal = principal;
        this.roles = roles;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return roles.contains(role);
    }

    @Override
    public boolean isSecure() {
        return requestContext.getUriInfo().getRequestUri().getScheme().equalsIgnoreCase("https");
    }

    @Override
    public String getAuthenticationScheme() {
        return "BEARER_TOKEN";
    }

}
