package com.scalefocus.cvmanager.controller;

import com.scalefocus.cvmanager.exception.MissingCredentialsException;
import com.scalefocus.cvmanager.exception.UsernameExistsException;
import com.scalefocus.cvmanager.model.user.UserEntity;
import com.scalefocus.cvmanager.service.SecurityUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mariyan Topalov
 */
@RestController
public class UserController {

    private final SecurityUserDetailsService userDetailsService;

    public UserController(SecurityUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register/")
    public ResponseEntity<Object> register(@RequestBody UserEntity userEntity) throws UsernameExistsException, MissingCredentialsException {
        userDetailsService.save(userEntity);
        return ResponseEntity.ok("User with username " + userEntity.getUsername() + " created!");
    }
}
