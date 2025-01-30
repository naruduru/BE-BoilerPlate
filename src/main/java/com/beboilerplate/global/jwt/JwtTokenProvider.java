package com.beboilerplate.global.jwt;

import com.beboilerplate.domain.member.entity.enums.Role;
import com.beboilerplate.domain.member.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Getter
@Component
public class JwtTokenProvider {

    private final Key key;
    private final long tokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    @Autowired
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000; // 1시간
        this.refreshTokenValidityInMilliseconds = tokenValidityInSeconds * 1000 * 24 * 7; // 7일
    }

    public String createAccessToken(String email, Role role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        Claims claims = getClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String email = claims.getSubject();
        String role = claims.get("role", String.class);

        return new UsernamePasswordAuthenticationToken(email, null,
                Collections.singletonList(new SimpleGrantedAuthority(role)));
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }
}
