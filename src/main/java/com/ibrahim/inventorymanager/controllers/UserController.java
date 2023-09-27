package com.ibrahim.inventorymanager.controllers;




import com.ibrahim.inventorymanager.dtos.BaseResponse;
import com.ibrahim.inventorymanager.entities.users.User;
import com.ibrahim.inventorymanager.dtos.AuthRequest;
import com.ibrahim.inventorymanager.services.JwtService;
import com.ibrahim.inventorymanager.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/addNewUser")
    public ResponseEntity<BaseResponse> addNewUser(@RequestBody User userInfo) {
        return service.addUser(userInfo);
    }


    @PutMapping("/{userID}/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<BaseResponse> updateUser(@RequestBody User userInfo, @RequestParam int userID) {
        return service.updateUser(userInfo, userID);
    }


    @GetMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
