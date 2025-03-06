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

package com.ericafenyo.carehub.web.controller;

import com.ericafenyo.carehub.contexts.CreateTaskContext;
import com.ericafenyo.carehub.dto.CreateTeamRequest;

import com.ericafenyo.carehub.dto.InvitationRequest;
import com.ericafenyo.carehub.dto.UpdateTeamRequest;
import com.ericafenyo.carehub.exceptions.HttpException;
import com.ericafenyo.carehub.model.Membership;
import com.ericafenyo.carehub.model.Note;
import com.ericafenyo.carehub.model.Task;
import com.ericafenyo.carehub.model.Team;
import com.ericafenyo.carehub.model.VitalMeasurement;
import com.ericafenyo.carehub.model.VitalReport;
import com.ericafenyo.carehub.requests.CreateNoteRequest;
import com.ericafenyo.carehub.requests.CreateTaskRequest;
import com.ericafenyo.carehub.domain.service.TeamService;
import com.ericafenyo.carehub.web.request.impl.CreateVitalReportRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class TeamController {
    private final TeamService service;

    @PostMapping("/teams")
    public Team createTeam(@RequestBody @Valid CreateTeamRequest request) {
        return service.createTeam(request.getName(), request.getDescription());
    }

    @PostMapping("/teams/{teamId}/notes")
    @PreAuthorize("hasAuthority('create:notes')")
    public Note createNote(
            @PathVariable("teamId") UUID teamId,
            @RequestBody CreateNoteRequest request
    ) {
        return service.createNote(request.getTitle(), request.getContent(), teamId);
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
        return service.addMember(
                teamId,
                request.getRole(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );
    }

    @GetMapping("/teams/{id}/members/{userId}")
    public Object getMember(
            @PathVariable("id") UUID teamId,
            @PathVariable("userId") UUID userId
    ) throws HttpException {
        return service.getMembership(teamId, userId);
    }

    @GetMapping("/teams/{id}/memberships")
    public List<Membership> getMembership(@PathVariable("id") UUID teamId) throws HttpException {
        return service.getTeamMembership(teamId);
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

    // Vitals sub-resources

    /**
     * Create a new vital report for a team
     */
    @PostMapping("/teams/{id}/vital-reports")
    public VitalReport createVitalReports(
            @PathVariable("id") UUID teamId,
            @RequestBody CreateVitalReportRequest request
    ) throws HttpException {
        return service.createVitalReports(teamId, request);
    }

    /**
     * Get a list of vital reports belonging to a team
     */
    @GetMapping("/teams/{id}/vital-reports")
    public List<VitalReport> getVitalReports(@PathVariable("id") UUID teamId) throws HttpException {
        return service.getVitalReports(teamId);
    }


    // Vitals sub-resources
    @GetMapping("/teams/{id}/vital-measurements")
    public List<VitalMeasurement> getVitals(@PathVariable("id") UUID teamId) throws HttpException {
        return service.getVitalMeasurements(teamId);
    }

    @GetMapping("/teams/{id}/vital-reports/{reportId}")
    public VitalReport getVitalReport(
            @PathVariable("id") UUID teamId,
            @PathVariable("reportId") UUID reportId
    ) {
        return service.getVitalReport(teamId, reportId);
    }
}
