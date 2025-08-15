package com.rofix.enotes_service.security.service;

import com.rofix.enotes_service.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService{

    @Value("${spring.jwt.secret}")
    private String jwtSecret;

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = getClaims(user);
        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60 * 15))
                .signWith(getKey())
                .compact();
    }

    private static Map<String, Object> getClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("full_name", user.getFullName());
        claims.put("first_name", user.getFirstName());
        claims.put("last_name", user.getLastName());
        claims.put("is_enabled", user.getStatus().getIsActive());
        claims.put("roles", user.getRoles());
        return claims;
    }

    private SecretKey getKey()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
    }
}
