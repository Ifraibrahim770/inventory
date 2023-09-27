package com.ibrahim.inventorymanager.services;

import com.ibrahim.inventorymanager.entities.users.User;
import com.ibrahim.inventorymanager.services.UserInfoDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class UserInfoDetailsTest {

    @Test
    public void testUserInfoDetails() {
        // Create a User object with sample data
        User user = new User();
        user.setName("testuser");
        user.setPassword("password");
        user.setRoles("ROLE_USER,ROLE_ADMIN");

        // Create UserInfoDetails instance
        UserInfoDetails userInfoDetails = new UserInfoDetails(user);

        // Validate UserDetails methods
        assertEquals("testuser", userInfoDetails.getUsername());
        assertEquals("password", userInfoDetails.getPassword());

        // Validate Granted Authorities
        Collection<? extends GrantedAuthority> authorities = userInfoDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));

        // Check account status methods
        assertTrue(userInfoDetails.isAccountNonExpired());
        assertTrue(userInfoDetails.isAccountNonLocked());
        assertTrue(userInfoDetails.isCredentialsNonExpired());
        assertTrue(userInfoDetails.isEnabled());
    }
}

