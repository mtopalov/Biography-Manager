package com.scalefocus.cvmanager.config.security;

/**
 * Constants which are used for the Security of the project.
 *
 * @author mariyan.topalov
 */
public final class SecurityConstants {

    public static final String AUTH_LOGIN_URL = "/token";

    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_TYPE = "JWT";

    /**
     * This class must never be instantiated.
     */
    private SecurityConstants() {
        throw new AssertionError();
    }
}