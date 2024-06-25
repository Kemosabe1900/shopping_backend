package com.example.CLproject.utils;

import com.example.CLproject.models.User;
import com.example.CLproject.models.dtos.IncomingUserDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtTokenUtil {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour in milliseconds

    @Value("${app.jwt.secret}") //taken
    private String SECRET_KEY;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    public boolean validateAccessToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException ex){
            LOGGER.error("JWT expired", ex.getMessage());
        }catch (IllegalArgumentException ex){
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        }catch (MalformedJwtException ex){
            LOGGER.error("JWT is invalid", ex);
        }catch (UnsupportedJwtException ex){
            LOGGER.error("JWt not supported", ex);
        }catch (SignatureException ex){
            LOGGER.error("Signature validation failed");
        }

        return  false;
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubject(String token){
        return parseClaims(token).getSubject();
    }

    public String generateAccessToken(User user){
        return Jwts.builder()
                .setSubject(String. valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .setIssuer("Project2")
                .setIssuedAt(new Date())
                .setIssuer("Project2")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
    public int extractUserId(String token){

        return Integer.parseInt(parseClaims(token).getSubject());
    }

    public String extractUsername(String token){
        return parseClaims(token).get("username", String.class);
    }

    public String extractRole(String token){
        return (String) parseClaims(token).get("role");
    }

}
