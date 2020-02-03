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


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO do a dummy implementation with millions of users
        //TODO fix password somehow
        auth.inMemoryAuthentication()
                .withUser("mariyan")
                .password(encoder.encode("mariyan"))
                .roles("USER", "ADMIN")
            .and()
                .withUser("user")
                .password(encoder.encode("password"))
                .roles("USER");
        //.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/biographies/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/biographies/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}