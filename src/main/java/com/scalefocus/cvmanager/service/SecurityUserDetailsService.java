package com.scalefocus.cvmanager.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mariyan Topalov
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> users = new HashMap<>();

    public SecurityUserDetailsService(PasswordEncoder encoder) {
        users.put("mariyan", new User("mariyan", encoder.encode("mariyan"), Arrays.asList(() -> "USER", () -> "ADMIN")));
        users.put("user", new User("user", encoder.encode("password"), Collections.singletonList(() -> "USER")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (users.containsKey(username)) {
            return users.get(username);
        }
        return null;
    }
}
