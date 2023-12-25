package com.labcenter.auth;

import com.labcenter.security.JwtUtil;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("auth")
public class AuthControl {
    @Inject
    private UserBoundary userBoundary;
    @Inject
    private JwtUtil jwt;

    @Path("login")
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String login(@Valid User user) {
        userBoundary.persist(user);
        System.out.println(user.getRoles());
        return jwt.generateToken(user.getUsername(), user.getRoles());
    }

    @Path("register")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public User register(User user) {
        userBoundary.persist(user);
        return user;
    }
}
