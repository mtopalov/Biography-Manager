package com.scalefocus.cvmanager.config.security;

/**
 * @author Mariyan Topalov
 */
public enum Authority {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    CREATOR("ROLE_CREATOR");

    Authority(String name) {
    }


}
