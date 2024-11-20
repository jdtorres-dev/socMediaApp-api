package com.socMediaApp.service;

import java.util.Date;
import org.springframework.stereotype.Service;

import com.socMediaApp.model.User;

import java.security.Key;
import org.springframework.http.HttpHeaders;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    static final long EXPIRATIONTIME = 86400000;
    // 1 day in ms.
    static final String PREFIX = "Bearer";
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

     // Generate signed JWT token
    public String getToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(
                        new Date(System.currentTimeMillis() +
                                EXPIRATIONTIME))
                .signWith(key)
                .compact();
        return token;
    }

      // Get a token from request Authorization header,
    // verify the token, and get username
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            String user = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX + " ",
                            ""))
                    .getBody()
                    .getSubject();
            if (user != null)
                return user;
        }
        return null;
    }

    @SuppressWarnings("deprecation")
public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)) // Set expiration date
                .claim("username", user.getUsername())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .signWith(SignatureAlgorithm.HS512, key) 
                .compact(); // Build the JWT
    }

}
