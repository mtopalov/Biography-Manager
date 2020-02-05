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
 * Controller that handles User registration requests.
 *
 * @author Mariyan Topalov
 */
@RestController
public class UserController {

    private final SecurityUserDetailsService userDetailsService;

    public UserController(SecurityUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Handles the registration of a User.
     *
     * @param userEntity the user credentials to be saved.
     * @return message to inform the invoker that the user is created.
     *
     * @throws UsernameExistsException     if the username is already taken
     * @throws MissingCredentialsException if the credentials are missing, the request body is not valid.
     */
    @PostMapping("/register/")
    public ResponseEntity<Object> register(@RequestBody UserEntity userEntity) throws UsernameExistsException, MissingCredentialsException {
        userDetailsService.save(userEntity);
        return ResponseEntity.ok("User with username " + userEntity.getUsername() + " created!");
    }
}
