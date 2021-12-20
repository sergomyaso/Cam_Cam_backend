package com.nsu.camcam.provider;

import com.nsu.camcam.model.Authority.Authority;
import com.nsu.camcam.model.Authority.Role;
import com.nsu.camcam.model.UserCredential;
import io.jsonwebtoken.*;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class JwtProvider {

    private static final int ACCESS_TOKEN_LIFE_TIME = 10; // 10 day of life access token
    private static final String ID_CLAIM = "userID";
    private static final String USERNAME_CLAIM = "username";
    private static final String AUTHORITIES_CLAIM = "authorities";

    private final org.apache.commons.logging.Log logger = LogFactory.getLog(getClass());

    @Value("${jwt.authsecret}")
    private String jwtAuthSecret;

    public String createJWTToken(UserCredential userCredential) {
        Date dateExpiration = Date.from(LocalDate.now().plusDays(ACCESS_TOKEN_LIFE_TIME).atStartOfDay(ZoneId.systemDefault()).toInstant());

        ArrayList<String> authorities = userCredential.getStringAuthorities();

        return Jwts.builder()
                .claim(ID_CLAIM, userCredential.getId().toString())
                .claim(USERNAME_CLAIM, userCredential.getUsername())
                .claim(AUTHORITIES_CLAIM, authorities)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtAuthSecret)
                .compact();
    }


    public boolean validateToken(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException unsEx) {
            logger.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            logger.error("Malformed jwt");
        } catch (SignatureException sEx) {
            logger.error("Invalid signature");
        } catch (Exception e) {
            logger.error("invalid token");
        }
        return false;
    }

    public boolean validateAuthJWTToken(String token) {
        return validateToken(token, jwtAuthSecret);
    }

    @SuppressWarnings("unchecked")
    public UserCredential getCredentialsFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtAuthSecret).parseClaimsJws(token).getBody();
        UserCredential credential = new UserCredential(
                Long.parseLong(claims.get(ID_CLAIM).toString()),
                claims.get(USERNAME_CLAIM).toString()
        );

        credential.setAuthorities(extractAuthorities((ArrayList<String>) claims.get(AUTHORITIES_CLAIM)));

        return credential;
    }

    private Set<Authority> extractAuthorities(ArrayList<String> stringRoles) {
        Set<Authority> authorities = new HashSet<>();
        stringRoles.forEach(stringRole -> {
            authorities.add(new Authority(Role.valueOf(stringRole)));
        });

        return Collections.unmodifiableSet(authorities);
    }

}