package com.labcenter;

import com.labcenter.auth.Role;
import com.labcenter.auth.User;
import com.labcenter.auth.UserBoundary;
import com.labcenter.pool.JpaPool;

import jakarta.enterprise.context.control.RequestContextController;
import jakarta.inject.Inject;
import java.util.List;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

@EnableWeld
public class UserTest {

    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(JpaPool.class, UserBoundary.class).build();
    @Inject
    private RequestContextController requestContextController;
    @Inject
    UserBoundary userBoundary;

    @Test
    public void persistUser() {
            requestContextController.activate();
            User user = new User();
            List<Role> lr = new ArrayList<>();
            lr.add(Role.ADMIN);
            lr.add(Role.USER);
            user.setEmail("emailuser@mail.com");
            user.setPassword("password");
            user.setUsername("username");
            user.setRoles(lr);

            userBoundary.persist(user);
            System.out.println(user.getId() + " <<<<");
            assertEquals("username", user.getUsername());
            requestContextController.deactivate();

    }
}
