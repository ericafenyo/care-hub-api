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

import com.ericafenyo.seniorhub.contexts.CreateNoteContext;
import com.ericafenyo.seniorhub.contexts.CreateTaskContext;
import com.ericafenyo.seniorhub.dto.CreateTeamRequest;
import com.ericafenyo.seniorhub.dto.UpdateTeamRequest;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.model.Invitation;
import com.ericafenyo.seniorhub.model.Note;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.model.Task;
import com.ericafenyo.seniorhub.model.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    Team createTeam(CreateTeamRequest request, UUID creatorId) throws HttpException;

    List<Team> getTeams();

    Team getTeamById(UUID id) throws HttpException;

    Team updateTeam(UUID id, UpdateTeamRequest userUpdateDto);

    void deleteTeam(UUID id);

    List<Team> getUserTeams(UUID id) throws HttpException;

    Report invite(UUID teamId, UUID inviterId, String role, String email) throws HttpException;

    Invitation validateInvitation(UUID teamId);

    Task createTask(CreateTaskContext context) throws HttpException;
}
