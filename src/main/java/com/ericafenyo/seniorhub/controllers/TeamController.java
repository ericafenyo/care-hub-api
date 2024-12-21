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

package com.ericafenyo.seniorhub.controllers;

import com.ericafenyo.seniorhub.contexts.CreateTaskContext;
import com.ericafenyo.seniorhub.dto.CreateTeamRequest;

import com.ericafenyo.seniorhub.dto.InvitationRequest;
import com.ericafenyo.seniorhub.dto.UpdateTeamRequest;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.model.Task;
import com.ericafenyo.seniorhub.model.Team;
import com.ericafenyo.seniorhub.requests.CreateTaskRequest;
import com.ericafenyo.seniorhub.services.TeamService;
import com.ericafenyo.seniorhub.util.Accounts;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TeamController {
    private final TeamService service;

    @PostMapping("/teams")
    public Team createTeam(@RequestBody @Valid CreateTeamRequest request) throws Exception {
        return service.createTeam(request.getName(), request.getDescription());
    }

    @GetMapping("/teams")
    public List<Team> getTeams() {
        return service.getTeams();
    }

    @GetMapping("/teams/{id}")
    public Team getUserById(@PathVariable @Valid @NotBlank UUID id) throws Exception {
        return service.getTeamById(id);
    }

    @PutMapping("/teams/{id}")
    public Team updateUser(
        @PathVariable @Valid @NotBlank UUID id,
        @RequestBody @Valid UpdateTeamRequest userUpdateDto
    ) {
        return service.updateTeam(id, userUpdateDto);
    }

    @DeleteMapping("/teams/{id}")
    public void deleteTeam(@PathVariable @Valid @NotBlank UUID id) {
        service.deleteTeam(id);
    }

    // Invitation sub-resources

    @PostMapping("/teams/{id}/invitations")
    public Object addMember(
        @PathVariable("id") UUID teamId,
        @RequestBody() InvitationRequest request
    ) throws HttpException {
        return service.invite(teamId, request.getRole(), request.getEmail());
    }

    // Task sub-resources

    @PostMapping("/teams/{id}/tasks")
    public Task createTask(
        @PathVariable UUID id,
        @RequestBody CreateTaskRequest request
    ) throws HttpException {


        var context = CreateTaskContext.builder()
            .setTitle(request.getTitle())
            .setDescription(request.getDescription())
            .setDueDate(request.getDueDate())
            .setPriority(request.getPriority())
            .setTeamId(id)
            .build();

        return service.createTask(context);
    }
}
