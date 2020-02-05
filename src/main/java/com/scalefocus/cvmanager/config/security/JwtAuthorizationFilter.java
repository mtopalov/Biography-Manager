package com.scalefocus.cvmanager.config.security;


import com.scalefocus.cvmanager.service.SecurityUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Security filter that serves for authorization.
 * Verifies the token, which is send as request header.
 *
 * @author Mariyan Topalov
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final SecurityUserDetailsService userDetailsService;

    public JwtAuthorizationFilter(SecurityUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = getAuthentication(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Gets the JWT from the request header and verifies it. If it's a legal JWT, an Authentication object is returned.
     *
     * @param request the request from which the token will be obtained.
     * @return an authenticated Authentication object if the token is a valid JWT.
     */
    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (isValid(token, SecurityConstants.TOKEN_PREFIX)) {
            try {
                String username = extractUserFromToken(token);

                UserDetails user = userDetailsService.loadUserByUsername(username);

                if (username != null && username.length() != 0) {
                    return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
                }
            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (SignatureException exception) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }
        }
        return null;
    }

    /**
     * Gets the username, which is hold in claims of the JWT.
     *
     * @param token the token from which the username will be retrieved.
     */
    private String extractUserFromToken(String token) {
        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
                .parseClaimsJws(extractToken(token));

        return parsedToken.getBody().getSubject();
    }

    /**
     * Extracts the actual token from the given argument.
     *
     * @param token the string from which the actual token will be extracted.
     * @return the actual token.
     */
    private String extractToken(String token) {
        return token.trim().split(" ")[1];
    }

    /**
     * Validates the token, given as argument.
     *
     * @param token the token
     * @param type  the type of the token
     * @return {@link Boolean#TRUE} if the token is valid.
     */
    private boolean isValid(String token, String type) {
        return token != null && token.length() > 0 && token.startsWith(type.trim());
    }
}
