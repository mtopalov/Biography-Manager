package com.scalefocus.cvmanager.model.jwt;

import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serves as JWT(Json Web Token) holder.
 *
 * @author Mariyan Topalov
 */
public final class JwtResponse {

    private final String token;

    private final String type;

    private final LocalDateTime expiresIn;

    public JwtResponse(String token, String type, LocalDateTime expiresIn) {
        this.token = token;
        this.type = type;
        this.expiresIn = expiresIn;
    }

    @SuppressWarnings("unchecked")
    public JSONObject asJson() {
        JSONObject result = new JSONObject();
        result.put("type", type.trim());
        result.put("token", token);
        result.put("expires_in", expiresIn.format(DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm:ss")));
        return result;
    }
}
