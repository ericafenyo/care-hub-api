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

import com.ericafenyo.seniorhub.entities.CredentialEntity;
import com.ericafenyo.seniorhub.entities.RoleEntity;
import com.ericafenyo.seniorhub.model.Account;
import com.ericafenyo.seniorhub.model.Role;
import com.ericafenyo.seniorhub.repository.CredentialRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
  private final UserRepository userRepository;
  private final CredentialRepository credentialRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getAccount(username);
  }

  public Account getAccount(String email) {
    var userResult = userRepository.findByEmail(email);

    if (userResult.isEmpty()) {
      throw new UsernameNotFoundException("");
    }
    var user = userResult.get();

    Optional<CredentialEntity> credentialResult = credentialRepository.findByUserId(user.getId());

    if (credentialResult.isEmpty()) {
      throw new UsernameNotFoundException("");
    }
    var credential = credentialResult.get();

    return Account
        .builder()
        .id(user.getUuid())
        .email(user.getEmail())
        .password(credential.getPassword())
        .role(user.getRole().getName())
        .build();
  }
}
