package com.scalefocus.cvmanager.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.cvmanager.model.jwt.JwtRequest;
import com.scalefocus.cvmanager.model.jwt.JwtResponse;
import com.scalefocus.cvmanager.util.DateUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Class that serves as Authentication filter.
 * Verifies the username and password that are given in the request.
 * If those are valid, a JWT is created and returned as header in the response.
 *
 * @author mariyan.topalov
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Log log = LogFactory.getLog(JwtAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        //the url at which the token can be obtained(login url)
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    /**
     * Gets the username and password from the argument request's body and verifies them.
     * If those are valid, the authentication attempt is successfull.
     *
     * @param request  the request.
     * @param response the response.
     * @return fully populated Authentication object if successful.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Optional<JwtRequest> jwtRequest = parseJwtRequest(request);
        Authentication authenticationToken = null;
        if (jwtRequest.isPresent()) {
            authenticationToken = new UsernamePasswordAuthenticationToken(jwtRequest.get().getUsername(), jwtRequest.get().getPassword());
        }
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * This method is called when the
     * {@link JwtAuthenticationFilter#attemptAuthentication} succeeded(returned fully populated Authentication object).
     * <p>
     * This method creates the JWT for the authenticated user, which later will serve as an authorization for the user.
     * When created, the JWT is returned to the user via response body
     *
     * @param request        the request
     * @param response       the response
     * @param filterChain    the filter chain to be executed
     * @param authentication the fully populated Authentication object, returned by "attemptAuthentication".
     * @throws IOException if IOException occurs.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {
        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
        User user = ((User) authentication.getPrincipal());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresIn = now.plusSeconds(3600);

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .setSubject(user.getUsername())
                .setIssuedAt(DateUtils.asDate(now))
                .setExpiration(DateUtils.asDate(expiresIn))
                .compact();

        JwtResponse jwtResponse = new JwtResponse(token, SecurityConstants.TOKEN_PREFIX.trim(), expiresIn);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(jwtResponse.asJson().toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Parses the request's body to {@link JwtRequest}.
     *
     * @param request from where the body will be get and parsed.
     * @return {@link JwtRequest} object with request's body parameters mapped to it.
     */
    private Optional<JwtRequest> parseJwtRequest(HttpServletRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return Optional.of(mapper.readValue(request.getInputStream(), JwtRequest.class));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}

