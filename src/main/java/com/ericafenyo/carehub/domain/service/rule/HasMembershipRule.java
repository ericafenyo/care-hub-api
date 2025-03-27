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

package com.ericafenyo.carehub.domain.service.rule;

import com.ericafenyo.carehub.Messages;
import com.ericafenyo.carehub.exceptions.DomainException;
import com.ericafenyo.carehub.exceptions.ForbiddenException;
import com.ericafenyo.carehub.repository.MembershipRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * A rule that checks if a user has a membership in a team.
 */
@Component
@RequiredArgsConstructor
public class HasMembershipRule implements Constraint<HasMembershipRule.Params> {
    private final MembershipRepository repository;
    private final Messages messages;

    @Override
    public void validate(Params params) throws DomainException {
        var membershipExists = repository.existsByTeamIdAndUserId(params.getTeamId(), params.getUserId());
        if (!membershipExists) {
            throw ForbiddenException.builder()
                    .message(messages.get("membership.error.user.not.member"))
                    .code("membership.error.user.not.member.code")
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Params {
        /**
         * The unique identifier of the team.
         */
        private UUID teamId;
        /**
         * The unique identifier of the user.
         */
        private UUID userId;
    }
}
