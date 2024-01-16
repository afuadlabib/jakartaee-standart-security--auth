package com.labcenter;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("test-role")
public class Hello {
    @GET
    @RolesAllowed("ADMIN")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(){
        return "Hello";
    }

}
