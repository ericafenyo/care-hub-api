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

package com.ericafenyo.seniorhub.util;

import com.ericafenyo.seniorhub.EnvironmentVariables;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * A utility class for managing JSON Web Tokens (JWT).
 * This class provides methods for signing, verifying and extracting JWT tokens.
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {
    // Used to access environment variables from the application.properties file
    private final EnvironmentVariables env;

    /**
     * Signs a JWT token with the given subject and claims.
     *
     * @param subject the subject of the token
     * @param claims  other claims to include in the token
     * @return the signed JWT token
     */
    public String sign(String subject, Map<String, ?> claims) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(env.getJwtExpirationTimeSeconds(), ChronoUnit.SECONDS);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * Extracts the claims from a JWT token.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     */
    Claims extract(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extracts a specific claim from a JWT token.
     *
     * @param token    the JWT token
     * @param resolver a function that extracts the desired claim from the token's claims
     * @param <T>      the type of the claim to extract
     * @return the extracted claim
     */
    public <T> T extract(String token, Function<Claims, T> resolver) {
        Claims claims = extract(token);
        return resolver.apply(claims);
    }

    /**
     * Verifies whether a JWT token has expired.
     *
     * @param token the JWT token
     * @return true if the token has expired, false otherwise
     */
    public boolean hasExpired(String token) {
        Date expiration = extract(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(env.getJwtSecret()));
    }
}
