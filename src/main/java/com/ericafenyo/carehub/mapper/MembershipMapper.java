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

package com.ericafenyo.carehub.mapper;

import com.ericafenyo.carehub.entities.MembershipEntity;
import com.ericafenyo.carehub.entities.RoleEntity;
import com.ericafenyo.carehub.model.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Maps a {@link MembershipEntity} to a {@link Membership}
 */
@Component
@RequiredArgsConstructor
public class MembershipMapper implements Function<MembershipEntity, Membership> {
    private final TeamMapper teamMapper;
    private final RoleMapper roleMapper;
    private final PartialUserMapper userMapper;

    @Override
    public Membership apply(MembershipEntity entity) {
        var membership = new Membership()
                .setId(entity.getId())
                .setStatus(entity.getStatus())
                .setCreatedAt(entity.getCreatedAt());

        Optional.ofNullable(entity.getTeam())
                .ifPresent(team -> membership.setTeam(teamMapper.apply(team)));

        Optional.ofNullable(entity.getUser())
                .map(userMapper)
                .ifPresent(user -> membership.setUser(user));

        Optional.ofNullable(entity.getRole())
                .ifPresent(role -> {
                    membership.setRole(roleMapper.apply(role));
//                    membership.setPermissions(constructPermissions(role));
                });

        return membership;
    }

    /**
     * Constructs a set of permissions string from a given role entit.
     *
     * @param role The role entity.
     * @return A set of permissions string.
     */
    private static Set<String> constructPermissions(RoleEntity role) {
        return role.getPermissions()
                .stream()
                .map(permission -> permission.getName())
                .collect(Collectors.toSet());
    }
}
