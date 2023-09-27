package com.ibrahim.inventorymanager.services;

import com.ibrahim.inventorymanager.dtos.BaseResponse;
import com.ibrahim.inventorymanager.entities.users.User;
import com.ibrahim.inventorymanager.repositories.UserRepository;
import com.ibrahim.inventorymanager.services.UserInfoDetails;
import com.ibrahim.inventorymanager.services.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserInfoServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserInfoService userInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "testuser";
        User user = new User();
        user.setName(username);
        user.setRoles("ROLE_ADMIN");
        when(userRepository.findByName(username)).thenReturn(Optional.of(user));

        UserInfoDetails userDetails = (UserInfoDetails) userInfoService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsernameUserNotFound() {
        String username = "nonexistentuser";
        when(userRepository.findByName(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(username));
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setName("testuser");
        user.setPassword("password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<BaseResponse> response = userInfoService.addUser(user);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User added successfully", response.getBody().getMessage());
        assertEquals("encodedPassword", user.getPassword()); // Password should be encoded
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setName("testuser");
        user.setRoles("ROLE_USER");
        user.setActive(true);
        int userId = 1;
        User userToBeUpdated = new User();
        userToBeUpdated.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userToBeUpdated));
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<BaseResponse> response = userInfoService.updateUser(user, userId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User added successfully", response.getBody().getMessage());
        assertEquals(user.getName(), userToBeUpdated.getName());
        assertEquals(user.getRoles(), userToBeUpdated.getRoles());
        assertEquals(user.isActive(), userToBeUpdated.isActive());
    }

    @Test
    public void testUpdateUserUserNotFound() {
        User user = new User();
        user.setName("testuser");
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<BaseResponse> response = userInfoService.updateUser(user, userId);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("user defined by id does not exist", response.getBody().getMessage());
    }
}

