package com.ibrahim.inventorymanager.controllers;

import com.ibrahim.inventorymanager.controllers.UserController;
import com.ibrahim.inventorymanager.dtos.AuthRequest;
import com.ibrahim.inventorymanager.dtos.BaseResponse;
import com.ibrahim.inventorymanager.entities.users.User;
import com.ibrahim.inventorymanager.services.JwtService;
import com.ibrahim.inventorymanager.services.UserInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewUser() {
        User user = new User();
        when(userInfoService.addUser(user)).thenReturn(new ResponseEntity<>(new BaseResponse(true, "User added"), HttpStatus.CREATED));

        ResponseEntity<BaseResponse> response = userController.addNewUser(user);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User added", response.getBody().getMessage());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        int userID = 1;
        when(userInfoService.updateUser(user, userID)).thenReturn(new ResponseEntity<>(new BaseResponse(true, "User updated"), HttpStatus.OK));

        ResponseEntity<BaseResponse> response = userController.updateUser(user, userID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated", response.getBody().getMessage());
    }

    @Test
    public void testAuthenticateAndGetToken() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken("username")).thenReturn("token");

        String token = userController.authenticateAndGetToken(authRequest);

        assertNotNull(token);
        assertEquals("token", token);
    }

    @Test
    public void testAuthenticateAndGetTokenInvalidUser() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new UsernameNotFoundException("invalid user request !"));

        assertThrows(UsernameNotFoundException.class, () -> userController.authenticateAndGetToken(authRequest));
    }
}

