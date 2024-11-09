package com.ceos20_instagram.global.jwt;

import com.ceos20_instagram.domain.member.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 60;

    private Key key;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public void afterPropertiesSet() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String createAccessToken(String username, Authentication authentication, UserRole role) {
        String authorities =
                authentication.getAuthorities()
                              .stream()
                              .map(GrantedAuthority::getAuthority)
                              .collect(Collectors.joining(","));

        Claims claims = (Claims) Jwts.claims()
                                     .setSubject(username);
        claims.put("auth", authorities);
        claims.put("role", role.name());

        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_SECONDS);

        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(now)
                   .signWith(key, SignatureAlgorithm.HS512)
                   .setExpiration(validity)
                   .compact();
    }

    public String getTokenUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject(); // get
    }


    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getRole(final String token) {
        return getPayload(token).get("role", String.class);
    }

    public String getUsername(final String token) {
        return getPayload(token).get("username", String.class);
    }

    private Claims getPayload(final String token) {
        return Jwts.parser()
                   .setSigningKey(secretKey)
                   .parseClaimsJws(token)
                   .getBody();
    }


}