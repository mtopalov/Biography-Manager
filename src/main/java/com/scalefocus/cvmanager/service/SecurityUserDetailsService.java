package com.scalefocus.cvmanager.service;

import com.scalefocus.cvmanager.exception.MissingCredentialsException;
import com.scalefocus.cvmanager.exception.UsernameExistsException;
import com.scalefocus.cvmanager.model.user.UserEntity;
import com.scalefocus.cvmanager.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mariyan Topalov
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    private final Map<String, UserDetails> loggedUsers = new HashMap<>();

    public SecurityUserDetailsService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loggedUsers.containsKey(username)) {
            return loggedUsers.get(username);
        }
        UserEntity byUsername = repository.findByUsername(username);
        UserDetails user = new User(byUsername.getUsername(), byUsername.getPassword(), getAuthorities(byUsername));
        loggedUsers.put(username, user);
        return user;
    }

    public void save(UserEntity userEntity) throws MissingCredentialsException, UsernameExistsException {
        if (userEntity == null || userEntity.getUsername() == null || userEntity.getPassword() == null) {
            throw new MissingCredentialsException("Username and password are required fields!");
        }
        if (repository.existsByUsername(userEntity.getUsername())) {
            throw new UsernameExistsException("The username already exists!");
        }

        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        repository.save(userEntity);
    }

    private List<GrantedAuthority> getAuthorities(UserEntity userEntity) {
        return userEntity.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
