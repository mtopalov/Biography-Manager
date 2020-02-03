package com.scalefocus.cvmanager.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.cvmanager.model.jwt.JwtRequest;
import com.scalefocus.cvmanager.model.jwt.JwtResponse;
import com.scalefocus.cvmanager.util.DateUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mariyan.topalov
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Optional<JwtRequest> jwtRequest = parseLoginData(request);
        Authentication authenticationToken = null;
        if (jwtRequest.isPresent()) {
            authenticationToken = new UsernamePasswordAuthenticationToken(jwtRequest.get().getUsername(), jwtRequest.get().getPassword());
        }


        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {
        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
        User user = ((User) authentication.getPrincipal());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresIn = now.plusSeconds(3600);
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.getUsername())
                .setIssuedAt(DateUtils.asDate(now))
                .setExpiration(DateUtils.asDate(expiresIn))
                .claim("roles", roles)
                .compact();

        JwtResponse jwtResponse = new JwtResponse(token, SecurityConstants.TOKEN_PREFIX.trim(), expiresIn);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(jwtResponse.asJson().toString());
        }
    }

    private Optional<JwtRequest> parseLoginData(HttpServletRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return Optional.of(mapper.readValue(request.getInputStream(), JwtRequest.class));
        } catch (IOException exception) {
            return Optional.empty();
        }
    }
}

