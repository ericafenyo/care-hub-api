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

package com.ericafenyo.seniorhub.repository;

import com.ericafenyo.seniorhub.entities.MembershipEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for performing database operations on a {@link com.ericafenyo.seniorhub.entities.MembershipEntity}.
 */
@Repository
public interface MembershipRepository extends AbstractRepository<MembershipEntity> {
    /**
     * Retrieves a team member by team id and user id.
     *
     * @param teamId The id of the team.
     * @param userId The id of the user.
     * @return The team member with the given team id and user id or {@literal Optional#empty()} if none found.
     */
    Optional<MembershipEntity> findByTeamIdAndUserId(UUID teamId, UUID userId);

    /**
     * Retrieves a list of team membership for a given user id.
     *
     * @param userId The unique identifier of the user.
     * @return A list of team membership for the given user id or an empty list if none found.
     */
    List<MembershipEntity> findByUserId(UUID userId);
}
