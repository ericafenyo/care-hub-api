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

package com.ericafenyo.seniorhub.implementation.services;

import com.ericafenyo.seniorhub.dto.CreateTeamRequest;
import com.ericafenyo.seniorhub.dto.UpdateTeamRequest;
import com.ericafenyo.seniorhub.entities.TeamEntity;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.exceptions.NotFoundException;
import com.ericafenyo.seniorhub.model.Team;
import com.ericafenyo.seniorhub.repository.TeamRepository;
import com.ericafenyo.seniorhub.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public Team createTeam(CreateTeamRequest request) throws HttpException {
        var exists = teamRepository.existsByName(request.getName());

        if (exists) {
            throw new NotFoundException("Team with name " + request.getName() + " already exists", "not-found");
        }

        var team = new TeamEntity();
        team.setName(request.getName());
        team.setDescription(request.getDescription());
        teamRepository.save(team);

        System.out.println(team);
        return null;
    }

    @Override
    public List<Team> getTeams() {
        return List.of();
    }

    @Override
    public Team getTeamById(String id) {
        return null;
    }

    @Override
    public Team updateTeam(String id, UpdateTeamRequest userUpdateDto) {
        return null;
    }

    @Override
    public void deleteTeam(String id) {

    }
}
