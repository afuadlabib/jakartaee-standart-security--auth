package com.labcenter;

import com.labcenter.auth.Role;
import com.labcenter.auth.User;
import com.labcenter.auth.UserBoundary;
import com.labcenter.pool.JpaPool;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.ActivateScopes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

@ActivateScopes(value = RequestScoped.class)
@ExtendWith(WeldJunit5Extension.class)
public class UserTest {
    

    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from( JpaPool.class,UserBoundary.class).build();
    @Inject
    UserBoundary userBoundary;
    @Test
    public void persistUser() {
        User user = new User();
        List<Role> lr = new ArrayList<>();
        lr.add(Role.ADMIN);
        lr.add(Role.USER);
        user.setEmail("emailuser@mail.com");
        user.setUsername("username");
        user.setRoles(lr);

        userBoundary.persist(user);
        System.out.println(user.getId() + " <<<<");
        assertEquals("username", user.getUsername());
    }
}
