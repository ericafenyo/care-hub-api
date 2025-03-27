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

import com.ericafenyo.carehub.contexts.CreateTaskContext;
import com.ericafenyo.carehub.model.Note;
import com.ericafenyo.carehub.domain.model.CreateVitalReportModel;
import com.ericafenyo.carehub.model.Membership;
import com.ericafenyo.carehub.dto.UpdateTeamRequest;
import com.ericafenyo.carehub.exceptions.DomainException;
import com.ericafenyo.carehub.model.Invitation;
import com.ericafenyo.carehub.model.Report;
import com.ericafenyo.carehub.model.Task;
import com.ericafenyo.carehub.model.Team;
import com.ericafenyo.carehub.model.VitalMeasurement;
import com.ericafenyo.carehub.model.VitalReport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing teams and related operations.
 */
public interface TeamService extends AbstractService {
    /**
     * Creates a new team.
     *
     * @param name        the name of the team
     * @param description the description of the team
     * @return the created team
     * @throws com.ericafenyo.carehub.exceptions.DomainException if an error occurs during creation
     */
    Team createTeam(String name, String description) throws DomainException;

    /**
     * Retrieves all teams.
     *
     * @return a list of teams
     */
    List<Team> getTeams();

    /**
     * Retrieves a team by its ID.
     *
     * @param teamId the ID of the team
     * @return the team with the specified ID
     * @throws com.ericafenyo.carehub.exceptions.DomainException if the team is not found
     */
    Team getTeamById(UUID teamId) throws DomainException;

    /**
     * Updates a team.
     *
     * @param teamId        the ID of the team to update
     * @param userUpdateDto the update request data
     * @return the updated team
     */
    Team updateTeam(UUID teamId, UpdateTeamRequest userUpdateDto);

    /**
     * Deletes a team.
     *
     * @param teamId the ID of the team to delete
     */
    void deleteTeam(UUID teamId);

    /**
     * Retrieves the teams of a user.
     *
     * @param teamId the ID of the user
     * @return a list of teams
     * @throws com.ericafenyo.carehub.exceptions.DomainException if an error occurs during retrieval
     */
    List<Team> getUserTeams(UUID teamId) throws DomainException;

    /**
     * Adds a member to a team.
     *
     * @param teamId    the ID of the team
     * @param role      the role of the new member
     * @param firstName the first name of the new member
     * @param lastName  the last name of the new member
     * @param email     the email of the new member
     * @return the report of the invitation
     * @throws com.ericafenyo.carehub.exceptions.DomainException if an error occurs during the addition
     */
    Report addMember(UUID teamId, String role, String firstName, String lastName, String email) throws DomainException;

    /**
     * Validates an invitation.
     *
     * @param teamId the ID of the team
     * @return the invitation
     */
    Invitation validateInvitation(UUID teamId);

    /**
     * Creates a new task.
     *
     * @param context the context of the task creation
     * @return the created task
     * @throws com.ericafenyo.carehub.exceptions.DomainException if an error occurs during creation
     */
    Task createTask(CreateTaskContext context) throws DomainException;

    /**
     * Retrieves a membership by team and user IDs.
     *
     * @param teamId the ID of the team
     * @param userId the ID of the user
     * @return the membership
     * @throws com.ericafenyo.carehub.exceptions.DomainException if the membership is not found
     */
    Membership getMembership(UUID teamId, UUID userId) throws DomainException;

    /**
     * Retrieves the memberships of a team.
     *
     * @param teamId the ID of the team
     * @return a list of memberships
     * @throws com.ericafenyo.carehub.exceptions.DomainException if an error occurs during retrieval
     */
    List<Membership> getTeamMembership(UUID teamId) throws DomainException;

    /**
     * Retrieves the memberships of a user.
     *
     * @param userId the ID of the user
     * @return a list of memberships
     * @throws com.ericafenyo.carehub.exceptions.DomainException if an error occurs during retrieval
     */
    List<Membership> getMemberships(UUID userId) throws DomainException;

    /**
     * Retrieves the vital reports of a team.
     *
     * @param teamId the ID of the team
     * @return a list of vital reports
     * @throws com.ericafenyo.carehub.exceptions.DomainException if an error occurs during retrieval
     */
    List<VitalReport> getVitalReports(UUID teamId) throws DomainException;

    /**
     * Creates a new vital report.
     *
     * @param teamId  the ID of the team
     * @param request the request data for creating the vital report
     * @return the created vital report
     * @throws com.ericafenyo.carehub.exceptions.DomainException if an error occurs during creation
     */
    VitalReport createVitalReports(UUID teamId, @Valid  CreateVitalReportModel request) throws DomainException;

    /**
     * Retrieves a vital report by team and report IDs.
     *
     * @param teamId   the ID of the team
     * @param reportId the ID of the report
     * @return the vital report
     */
    VitalReport getVitalReport(UUID teamId, UUID reportId);

    /**
     * Retrieves the vital measurements of a team.
     *
     * @param teamId the ID of the team
     * @return a list of vital measurements
     */
    List<VitalMeasurement> getVitalMeasurements(UUID teamId);

    Note createNote(@NotBlank String title, @NotBlank String content, UUID teamId);
}
