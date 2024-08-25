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

package com.ericafenyo.seniorhub.controllers;

import com.ericafenyo.seniorhub.Constants;
import com.ericafenyo.seniorhub.api.Tuple;
import com.ericafenyo.seniorhub.dto.BasicAuthenticateRequest;
import com.ericafenyo.seniorhub.dto.VerifyEmailDto;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.model.Tokens;
import com.ericafenyo.seniorhub.services.AccountService;
import com.ericafenyo.seniorhub.services.EmailPasswordAuthenticationService;
import com.ericafenyo.seniorhub.services.JwtAuthenticationService;
import com.ericafenyo.seniorhub.services.MailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor()
public class AuthenticationController {
  private final MailService service;
  private final JwtAuthenticationService jwtAuthenticationService;
  private final EmailPasswordAuthenticationService emailPasswordAuthenticationService;
  private final AccountService accountService;

  @PostMapping("/auth/login")
  public Tokens authenticate(@RequestBody BasicAuthenticateRequest request) throws HttpException {
    emailPasswordAuthenticationService.authenticate(request.getEmail(), request.getPassword());

    // Generate token
    var account = accountService.getAccount(request.getEmail());
    String token = jwtAuthenticationService.sign(account);

    return Tokens.builder()
        .refreshToken("")
        .accessToken(token)
        .build();
  }

  @PostMapping("/auth/verify-email")
  public Report sendVerificationCode(@RequestBody @Valid VerifyEmailDto verifyEmailDto, HttpServletResponse response) {
    Tuple<Report, String> result = service.sendVerificationCode(verifyEmailDto.getEmail());

    // Set HTTP-only cookie
    var cookie = new Cookie(Constants.COOKIES_EMAIL_VERIFICATION_CODE_KEY, result.second());
    cookie.setHttpOnly(true);
    cookie.setMaxAge(3600);  // Set cookie expiration time (e.g., 1 hour)
    cookie.setPath("/");     // Set cookie path

    // Add the cookie to the response
    response.addCookie(cookie);

    return result.first();
  }

  @PostMapping("/auth/verify-code")
  String verifyCode() {
    return null;
  }
}
