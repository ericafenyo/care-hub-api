/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2025 Eric Afenyo
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

package com.ericafenyo.carehub.domain.service;


import com.ericafenyo.carehub.Messages;
import com.ericafenyo.carehub.entities.UserEntity;
import com.ericafenyo.carehub.exceptions.NotFoundException;
import com.ericafenyo.carehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindUserByIdDelegate {
    private final UserRepository repository;

    private final Messages messages;

    /**
     * Find a user by the provided id.
     *
     * @param userId The id of the user to find.
     * @return The user entity if found.
     * @throws com.ericafenyo.carehub.exceptions.NotFoundException If the user is not found, a NotFoundException is thrown.
     */
    public UserEntity findUserById(UUID userId) throws NotFoundException {
        return repository.findById(userId)
                .orElseThrow(() -> new NotFoundException(messages.get("user.error.resource.not.found")));
    }
}
