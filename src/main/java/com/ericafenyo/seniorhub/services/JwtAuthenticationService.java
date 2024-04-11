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


import com.ericafenyo.seniorhub.Environment;
import com.ericafenyo.seniorhub.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
  public static final String EMAIL_KEY = "email";
  public static final String JWT_SECRET_KEY = "JWT_SECRET_KEY";

  private final Environment environment;

  public <T> T extract(String token, Function<Claims, T> resolver) {
    Claims claims = extract(token);
    return resolver.apply(claims);
  }

  private Claims extract(String token) {
    return Jwts.parser()
        .setSigningKey(environment.getJwtSecretKey())
        .parseClaimsJws(token)
        .getBody();
  }

  public void sign(Account account) {
    Instant issuedAt = Instant.now();
    Instant expiration = issuedAt.plus(24, ChronoUnit.HOURS);

    Jwts.builder()
        .claim(EMAIL_KEY, account.getEmail())
        .setSubject("auth|%s".formatted(account.getId()))
        .setIssuer("http://localhost/senoir-hub")
        .setIssuedAt(Date.from(issuedAt))
        .setExpiration(Date.from(expiration))
        .setAudience("aud")
        .signWith(SignatureAlgorithm.HS256, "secret")
        .compact();
  }

  public void verify(String token, Account account) {
    var isEa = hasEqualEmails(token, account);
  }

  private boolean hasEqualEmails(String token, Account account) {
    return account.getEmail().equalsIgnoreCase(extractEmail(token));
  }

  private String extractEmail(String token) {
    return extract(token, (claims -> claims.get(EMAIL_KEY, String.class)));
  }
}
