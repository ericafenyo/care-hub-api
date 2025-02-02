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
import com.ericafenyo.carehub.repository.CredentialRepository;
import com.ericafenyo.carehub.repository.MembershipRepository;
import com.ericafenyo.carehub.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final MembershipRepository membershipRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getAccount(username);
    }

    public Account getAccount(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username '" + email + "' not found"));

        var credential = credentialRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Credential for username '" + email + "' not found"));

        return new Account()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setPassword(credential.getPassword());
    }

    public Account getAccount(UUID membershipId) {
        var membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new UsernameNotFoundException("Membership with id '" + membershipId + "' not found"));

        var user = membership.getUser();

        var credential = credentialRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Credential for username '" + user.getEmail() + "' not found"));

        var role = membership.getRole();

        var permissions = role.getPermissions();

        return new Account()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setPassword(credential.getPassword())
                .setPermissions(permissions.stream().map(permission -> permission.getName()).toList());
    }
}
