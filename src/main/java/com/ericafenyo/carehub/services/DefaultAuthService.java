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

package com.ericafenyo.carehub.services;

import com.ericafenyo.carehub.model.Account;
import com.ericafenyo.carehub.model.Tokens;
import com.ericafenyo.carehub.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import static com.ericafenyo.carehub.model.Tokens.TokenType.ACCESS_TOKEN;
import static com.ericafenyo.carehub.model.Tokens.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {
    private static final String KEY_EMAIL = "email";

    private final JwtUtils jwt;
    private final AuthenticationManager authenticationManager;

    @Override
    public Tokens authenticate(String email, String password) {
        var credentials = new UsernamePasswordAuthenticationToken(email, password);
        var authentication = authenticationManager.authenticate(credentials);
        var account = Account.from(authentication);
        return generateAuthenticationTokens(account.getEmail(), account.getId());
    }

    /**
     * generates authentication tokens for the given email and user id
     *
     * @param email  the email of the user
     * @param userId the id of the user
     * @return a {@link Tokens} object containing the access and refresh tokens
     */
    private Tokens generateAuthenticationTokens(String email, UUID userId) {
        var subject = "auth|%s".formatted(userId);
        var accessToken = jwt.sign(subject, Map.of(KEY_EMAIL, email));
        var refreshToken = generateRefreshToken();

        return Tokens.create()
                .set(ACCESS_TOKEN, accessToken)
                .set(REFRESH_TOKEN, refreshToken);
    }

    private String generateRefreshToken() {
        String uuid = UUID.randomUUID().toString();
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(uuid.getBytes());
    }
}
