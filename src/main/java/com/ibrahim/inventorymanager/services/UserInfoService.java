package com.ibrahim.inventorymanager.services;

import com.ibrahim.inventorymanager.dtos.BaseResponse;
import com.ibrahim.inventorymanager.entities.users.User;


import com.ibrahim.inventorymanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserInfoService implements UserDetailsService{
    @Autowired
    private UserRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = userInfoRepository.findByName(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public ResponseEntity<BaseResponse> addUser(User userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return ResponseEntity.ok(new BaseResponse(true,"User added successfully"));
    }

    public ResponseEntity<BaseResponse> updateUser(User userInfo, int id) {

        User userToBeUpdated = userInfoRepository.findById(id).orElse(null);
        if (userToBeUpdated == null){
            return ResponseEntity.badRequest().body(new BaseResponse(false, "user defined by id does not exist"));

        }

        userToBeUpdated.setName(userInfo.getName());
        userToBeUpdated.setRoles(userInfo.getRoles());
        userToBeUpdated.setActive(userInfo.isActive());



        userInfoRepository.save(userInfo);
        return ResponseEntity.ok(new BaseResponse(true,"User added successfully"));
    }
}
