package com.scalefocus.cvmanager.config.security;

import com.scalefocus.cvmanager.service.SecurityUserDetailsService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configures the web security.
 *
 * @author mariyan.topalov
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityUserDetailsService userDetailsService;

    private final PasswordEncoder encoder;

    public SecurityConfig(SecurityUserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    /**
     * Configures the Authentication manager.
     * In this implementation, the authentication manager will work with custom {@link org.springframework.security.core.userdetails.UserDetailsService}.
     *
     * @param auth the authentication manager
     * @throws Exception if exception is raided.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }

    /**
     * Configures the {@link HttpSecurity}.
     *
     * @param http the http security to be configured.
     * @throws Exception if exception is raised when configuring the http security.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register/").permitAll()
                .antMatchers(HttpMethod.GET, "/biographies/**").hasAuthority(Authority.USER.name())
                .antMatchers(HttpMethod.POST, "/biographies/**").hasAuthority(Authority.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtAuthorizationFilter(userDetailsService), JwtAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}