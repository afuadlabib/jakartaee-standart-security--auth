package com.labcenter.security;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

@Provider
@RequestScoped
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilterSecurity implements ContainerRequestFilter {
    private final Logger log = Logger.getLogger(AuthenticationFilterSecurity.class.getName());
    @Inject
    private JwtUtil jwtUtil;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.info("doFilter");
        if (requestContext.getUriInfo().getPath().contains("/auth")) {
            return;
        }
        String headerString = requestContext.getHeaderString("Authorization");
        if (!headerString.isEmpty() && headerString.startsWith("Bearer ")) {
            log.info("auth: " + headerString);
            String token = headerString.substring(7);
            Principal p = new UserPrincipal(jwtUtil.extractUsername(token));
            List<String> roles = jwtUtil.extractRoles(token);
            SecurityContext sc = new IdentitySecurityContext(p, roles);

            requestContext.setSecurityContext(sc);
            return;
        }

        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

}
