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

package com.ericafenyo.seniorhub.mapper;

import com.ericafenyo.seniorhub.entities.UserEntity;
import com.ericafenyo.seniorhub.model.SealedRole;
import com.ericafenyo.seniorhub.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

/**
 * Maps a {@link UserEntity} to a {@link User}.
 */
@Component
@RequiredArgsConstructor
public class UserMapper implements Function<UserEntity, User> {
    private final AddressMapper addressMapper;

    /**
     * Converts a {@link UserEntity} to a {@link User}.
     *
     * @param entity The input user entity.
     * @return The mapped user.
     */
    @Override
    public User apply(UserEntity entity) {
        var user = new User();
        user.setId(entity.getUuid());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setBirthDate(entity.getBirthDate());
        user.setEmail(entity.getEmail());
        user.setPhotoUrl(entity.getPhotoUrl());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());


        Optional.ofNullable(entity.getAddress())
            .ifPresent(address -> user.setAddress(addressMapper.apply(address)));

        return user;
    }
}
