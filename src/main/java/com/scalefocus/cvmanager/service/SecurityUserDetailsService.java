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
 * This implementation of {@link UserDetailsService} handles the user registration, authentication and authorization of the users.
 * Users are being stored in database and loaded from there.
 *
 * @author Mariyan Topalov
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    /**
     * Cache-ing the logged users. Cuts the queries to the database in half for every authentication + authorization.
     */
    private final Map<String, UserDetails> loggedUsers = new HashMap<>();

    public SecurityUserDetailsService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    /**
     * Loads a user from the database by given username. Loaded users are being cached in {@link SecurityUserDetailsService#loggedUsers}.
     * Once they are cached, they will be loaded from the cache whenever their details are queried.
     * If the username does not exist, {@link UsernameNotFoundException} is thrown.
     *
     * @param username username to be searched for, and if exists - the user with that username will be loaded and returned.
     * @return {@link UserDetails}
     *
     * @throws UsernameNotFoundException if User with the given username does not exist.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loggedUsers.containsKey(username)) {
            return loggedUsers.get(username);
        }
        UserEntity byUsername = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with that username!"));
        UserDetails user = new User(byUsername.getUsername(), byUsername.getPassword(), getAuthorities(byUsername));
        loggedUsers.put(username, user);
        return user;
    }

    /**
     * Saves the given {@link UserEntity} into the database if it's username is not used already.
     * Throws {@link UsernameExistsException} if the {@link UserEntity}s username is already taken.
     *
     * @param userEntity the User to be saved
     * @throws UsernameExistsException if the user's username, to be saved, is already taken.
     */
    public void save(UserEntity userEntity) throws UsernameExistsException, MissingCredentialsException {
        if (isValid(userEntity)) {
            //encodes the password before saving it
            userEntity.setPassword(encoder.encode(userEntity.getPassword()));
            repository.save(userEntity);
        }
    }

    /**
     * Transforms the {@link List<String>} authorities, which is gotten from {@link UserEntity#getAuthorities()}
     * to {@link List<GrantedAuthority>}.
     *
     * @param userEntity the user which authorities will be transformed
     * @return user's authorities, which are List<String>, transformed to List<GrantedAuthority>
     */
    private List<GrantedAuthority> getAuthorities(UserEntity userEntity) {
        return userEntity.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Validates the given {@link UserEntity}.
     *
     * @param user to be validated.
     * @return true if the user is valid.
     *
     * @throws MissingCredentialsException if user's username or/and password are not given.
     * @throws UsernameExistsException     if username is already registered in the database.
     */
    private boolean isValid(UserEntity user) throws MissingCredentialsException, UsernameExistsException {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            throw new MissingCredentialsException("Username and password are required fields!");
        }
        if (repository.existsByUsername(user.getUsername())) {
            throw new UsernameExistsException("The username already exists!");
        }
        return true;
    }
}
