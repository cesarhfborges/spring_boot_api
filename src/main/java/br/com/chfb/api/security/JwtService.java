package br.com.chfb.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

//    private static final String SECRET = "229e7184ffaf9a2d31c6bd94f118d99348ea210a744679dd5f3881c64ac99753";
//
//    private static final long EXPIRATION = 1000 * 60 * 60 * 5; // 5h
//
//    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
//
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .subject(username)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
//                .signWith(key)
//                .compact();
//    }

    private final JwtProperties properties;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(properties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + properties.getExpirationTime()))
                .signWith(getSigningKey())
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public LocalDateTime getExpirationDateTime() {
        return Instant
                .ofEpochMilli(
                        System.currentTimeMillis() + properties.getExpirationTime()
                ).atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
