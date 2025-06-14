// src/main/java/com/snapthetitle/backend/util/JwtUtil.java
package com.snapthetitle.backend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // 실제 운영 환경에선 외부 설정으로 관리하세요
    private final Key key = Keys.hmacShaKeyFor("ThisIsASecretKeyForJwtTokenGeneration123456".getBytes());
    private final long validityMillis = 1000 * 60 * 60 * 4; // 4시간

    public String generateToken(String username, String role) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
