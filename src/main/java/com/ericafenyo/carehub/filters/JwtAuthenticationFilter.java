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

package com.ericafenyo.carehub.filters;

import com.ericafenyo.carehub.model.Account;
import com.ericafenyo.carehub.services.AccountService;
import com.ericafenyo.carehub.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static com.ericafenyo.carehub.Constants.KEY_EMAIL;
import static com.ericafenyo.carehub.Constants.KEY_MEMBERSHIP;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // The additional space at the end is required. Do not delete it.
    public static final String BEARER_AUTH_SCHEME = "bearer ";

    private final JwtUtils jwtUtils;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isNotAuthenticated() && hasValidAuthenticationToken(header)) {
            String jwt = header.substring(BEARER_AUTH_SCHEME.length());

            String email = jwtUtils.extract(jwt, (claims) -> claims.get(KEY_EMAIL, String.class));
            String membershipId = jwtUtils.extract(jwt, (claims) -> claims.get(KEY_MEMBERSHIP, String.class));

            Account account = membershipId != null
                    ? accountService.getAccount(UUID.fromString(membershipId))
                    : accountService.getAccount(email);

            if (account != null) {
                // Validate the JWT token and authenticate the user
                if (!jwtUtils.hasExpired(jwt)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isNotAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || !authentication.isAuthenticated();
    }

    private static boolean hasValidAuthenticationToken(String header) {
        return (header != null) && header.toLowerCase().startsWith(BEARER_AUTH_SCHEME);
    }
}
