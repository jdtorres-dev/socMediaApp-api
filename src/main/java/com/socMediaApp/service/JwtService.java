package com.socMediaApp.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.socMediaApp.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET_KEY = "d047b661f5de632cd945f1b63d55a44acefba0972d829707070998d44bfce253b80c21627e1f293cf5e42663c20b60a4e5bcef0f47b31877cc2b86632b7fbc8f3402b3a7454a0ff8222d66115714b169e2db4cf28fc617596f4bf0a50cfe7e6b48308da56563f1128c74f8faaae1c43fb7c6a278479ad8fcc53220465729690843c10d182d9dbb70ac43a7121f298bff2009bb78eff5e5b2e5fc9d7f8eb9070b0f9fe377b835f26149354545f609b6d0b88dab33aeacce01e35526eda490223ab5aed1ba634e652a9b870e9676696ef11992ccfa2965b4db182bd50be9b92f626148c1d9c68473509f743595777136f18127b04a5635c68fc7500cb1cc36b838";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
        .parser()
        .verifyWith(getSigninKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }

    public String generateToken(User user) {
        String token = Jwts
                    .builder()
                    .subject(user.getId().toString())
                    .claim("username", user.getUsername())
                    .claim("name", user.getName())
                    .claim("email", user.getEmail())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 86_400_000))
                    .signWith(getSigninKey())
                    .compact();

        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String getUsernameFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("username", String.class); 
    }

    public String getNameFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("name", String.class); 
    }

    public String getEmailFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("email", String.class); 
    }

}
