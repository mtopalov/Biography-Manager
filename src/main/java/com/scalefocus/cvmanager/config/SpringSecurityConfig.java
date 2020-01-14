package com.scalefocus.cvmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Mariyan Topalov
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("kris").password("{noop}123456").roles("USER")
                .and()
                .withUser("mariyan").password("{noop}654321").roles("USER", "ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/biographies/").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/biographies/**/pdf").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/biographies/**/word").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/biographies/").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/biographies/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("kris").password("123456").roles("USER").build());
        manager.createUser(users.username("mariyan").password("654321").roles("USER", "ADMIN").build());
        return manager;
    }
}
