/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2024 Eric Afenyo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ericafenyo.seniorhub.services;


import com.ericafenyo.seniorhub.EnvironmentVariables;
import com.ericafenyo.seniorhub.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
    private static final String EMAIL_KEY = "email";
    private static final String ROLE_KEY = "role";

    private final EnvironmentVariables env;

    public <T> T extract(String token, Function<Claims, T> resolver) {
        Claims claims = extract(token);
        return resolver.apply(claims);
    }

    private Claims extract(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String sign(Account account) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(env.getJwtExpirationTimeSeconds(), ChronoUnit.SECONDS);

        return Jwts.builder()
                .claim(EMAIL_KEY, account.getEmail())
                .subject("auth|%s".formatted(account.getId()))
                .issuer(env.getJwtIssuer())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * Checks if the token is valid.
     *
     * @param token   The token to be verified.
     * @param account The store user account.
     * @return true if the token is valid, false otherwise.
     */
    public boolean verify(String token, Account account) {
        var claims = extract(token);
        var expiresAt = claims.getExpiration();
        var hasEqualUserIds = claims.getSubject().equals("auth|%s".formatted(account.getId()));

        // Check if the token is blank, expired, or will expire soon
        boolean invalidToken = !hasEqualUserIds || token.isBlank() || hasExpired(expiresAt) || willExpire(expiresAt, Duration.ofMinutes(1));

        return !invalidToken;
    }


    private boolean hasExpired(Date expiredAt) {
        return expiredAt.before(new Date());
    }

    /**
     * Checks (given the required minimum time to live), if the expiration time can satisfy that value or not.
     *
     * @param expiresAt the expiration time.
     * @param minTtl    the time to live required.
     * @return whether the value will become expired within the given min offset to live or not.
     */
    private boolean willExpire(Date expiresAt, Duration minTtl) {
        Date now = new Date();
        // Calculate the minimum expiration time
        Date minExpirationTime = new Date(now.getTime() + minTtl.toMillis());

        return expiresAt.before(minExpirationTime);
    }

    public String extractEmail(String token) {
        return extract(token, (claims -> claims.get(EMAIL_KEY, String.class)));
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(env.getJwtSecret()));
    }
}
