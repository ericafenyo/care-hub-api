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

import com.ericafenyo.seniorhub.entities.TeamEntity;
import com.ericafenyo.seniorhub.model.Team;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Maps a {@link TeamEntity} to a {@link Team}.
 */
@Component
public class TeamMapper implements Function<TeamEntity, Team> {

    /**
     * Converts a {@link TeamEntity} to a {@link Team}.
     *
     * @param entity The input team entity.
     * @return The mapped team.
     */
    @Override
    public Team apply(TeamEntity entity) {
        var team = new Team();
        team.setId(entity.getUuid());
        team.setName(entity.getName());
        team.setDescription(entity.getDescription());
        team.setCreatedAt(entity.getCreatedAt());
        team.setUpdatedAt(entity.getUpdatedAt());

        return team;
    }
}
