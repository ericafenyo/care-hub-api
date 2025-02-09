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

package com.ericafenyo.carehub.implementation.services;

import com.ericafenyo.carehub.Messages;
import com.ericafenyo.carehub.contexts.CreateTaskContext;
import com.ericafenyo.carehub.core.AuthenticationContext;
import com.ericafenyo.carehub.dto.CreateVitalReportRequest;
import com.ericafenyo.carehub.dto.UpdateTeamRequest;
import com.ericafenyo.carehub.entities.TaskEntity;
import com.ericafenyo.carehub.entities.TeamEntity;
import com.ericafenyo.carehub.entities.MembershipEntity;
import com.ericafenyo.carehub.exceptions.ConflictException;
import com.ericafenyo.carehub.exceptions.HttpException;
import com.ericafenyo.carehub.exceptions.NotFoundException;
import com.ericafenyo.carehub.interactor.FindTeamByIdInteractor;
import com.ericafenyo.carehub.mapper.MembershipMapper;
import com.ericafenyo.carehub.mapper.TaskMapper;
import com.ericafenyo.carehub.mapper.TeamMapper;
import com.ericafenyo.carehub.model.Invitation;
import com.ericafenyo.carehub.model.Membership;
import com.ericafenyo.carehub.model.Report;
import com.ericafenyo.carehub.model.Role;
import com.ericafenyo.carehub.model.Task;
import com.ericafenyo.carehub.model.Team;
import com.ericafenyo.carehub.model.VitalMeasurement;
import com.ericafenyo.carehub.model.VitalReport;
import com.ericafenyo.carehub.repository.RoleRepository;
import com.ericafenyo.carehub.repository.TaskRepository;
import com.ericafenyo.carehub.repository.MembershipRepository;
import com.ericafenyo.carehub.repository.TeamRepository;
import com.ericafenyo.carehub.repository.UserRepository;
import com.ericafenyo.carehub.services.InvitationService;
import com.ericafenyo.carehub.services.TeamService;
import com.ericafenyo.carehub.services.VitalMeasurementService;
import com.ericafenyo.carehub.services.VitalReportService;
import com.ericafenyo.carehub.services.VitalService;
import com.ericafenyo.carehub.services.validation.Validations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultTeamService extends AuthenticationContext implements TeamService {
    private final VitalService vitalService;
    private final VitalReportService vitalReportService;
    private final TeamMapper mapper;
    private final TaskMapper taskMapper;

    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MembershipRepository teamMemberRepository;

    private final InvitationService invitationService;
    private final Messages messages;

    private final Validations validations;
    private final MembershipRepository membershipRepository;

    private final MembershipMapper membershipMapper;
    private final VitalMeasurementService vitalMeasurementService;

    @Override
    public Team createTeam(String name, String description) throws HttpException {
//        validations.validateTeamExists();

        if (teamRepository.existsByName(name)) {
            throw new ConflictException("Team with name " + name + " already exists", "not-found");
        }

        var userId = getAuthenticatedUserId();

        var owner = userRepository.findById(userId).get();
        var role = roleRepository.findBySlug(Role.SLUG_OWNER).get();

        var team = new TeamEntity();
        team.setName(name);
        team.setDescription(description);
        team.setOwner(owner);

        var member = new MembershipEntity()
                .setTeam(team)
                .setUser(owner)
                .setStatus(Membership.Status.ACTIVE)
                .setRole(role);

        teamMemberRepository.save(member);

        return mapper.apply(teamRepository.save(team));
    }

    @Override
    public List<Team> getTeams() {
        return teamRepository.findAll().stream()
                .map(mapper)
                .toList();
    }

    @Override
    public Team getTeamById(UUID teamId) throws HttpException {
        return findTeamById(teamId).map(mapper);
    }

    @Override
    public Team updateTeam(UUID id, UpdateTeamRequest userUpdateDto) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        team.setName(userUpdateDto.getName());
        team.setDescription(userUpdateDto.getDescription());

        return teamRepository.save(team).map(mapper);
    }

    @Override
    public void deleteTeam(UUID id) {
    }

    @Override
    public List<Team> getUserTeams(UUID teamId) throws HttpException {
        return teamRepository.findAllByOwnerId(teamId)
                .stream()
                .map(mapper)
                .toList();
    }

    @Override
    public Report addMember(
            UUID teamId,
            String role,
            String firstName,
            String lastName,
            String email
    ) throws HttpException {
        return invitationService.invite(teamId, role, firstName, lastName, email);
    }

    @Override
    public Invitation validateInvitation(UUID id) {
        return null;
    }

    @Override
    public Task createTask(CreateTaskContext context) throws HttpException {
        var team = findTeamById(context.getTeamId());

        var entity = new TaskEntity()
                .setTitle(context.getTitle())
                .setDescription(context.getDescription())
                .setDueDate(context.getDueDate())
                .setPriority(context.getPriority())
                .setTeam(team);

        TaskEntity task = taskRepository.save(entity);

        return taskMapper.apply(task);
    }

    /**
     * Find a team by the provided id.
     *
     * @param teamId The id of the team to find.
     * @return The team entity if found.
     * @throws NotFoundException If the team is not found, a NotFoundException is thrown.
     */
    private TeamEntity findTeamById(UUID teamId) throws NotFoundException {
        return teamRepository.findById(teamId).orElseThrow(() ->
                new NotFoundException(
                        messages.format("error.resource.not.found", "Team"),
                        messages.format("error.resource.not.found.code", "team")
                )
        );
    }

    @Override
    public Membership getMembership(UUID teamId, UUID userId) throws HttpException {
        if (!teamRepository.existsById(teamId)) {
            throw new NotFoundException(
                    messages.format("error.resource.not.found", "Team", teamId),
                    messages.format("error.resource.not.found.code", "team")
            );
        }

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(
                    "messages.format(Messages.ERROR_RESOURCE_WITH_ID_NOTFOUND, User, userId)",
                    "messages.format(Messages.ERROR_RESOURCE_NOTFOUND_CODE, user)"
            );
        }

        var member = teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> new NotFoundException(
                        "messages.format(Messages.ERROR_RESOURCE_NOTFOUND, Membership)",
                        "messages.format(Messages.ERROR_RESOURCE_NOTFOUND_CODE, membership)"
                ));

        return membershipMapper.apply(member);
    }

    @Override
    public Membership getMembership(UUID teamId) throws HttpException {
        return getMembership(teamId, getAuthenticatedUserId());
    }

    @Override
    public List<Membership> getMemberships(UUID userId) {
        return membershipRepository.findByUserId(userId)
                .stream()
                .map(membershipMapper)
                .toList();
    }

    @Override
    public List<VitalReport> getVitalReports(UUID teamId) {
        return vitalReportService.getVitalReports(teamId);
    }

    @Override
    public VitalReport createVitalReports(UUID teamId, CreateVitalReportRequest request) throws NotFoundException {
        var team = findTeamById(teamId);
        var user = userRepository.findById(getAuthenticatedUserId()).get();

        return vitalReportService.createVitalReport(team, user, request);
    }

    @Override
    public VitalReport getVitalReport(UUID teamId, UUID reportId) {
        validations.validateTeamExistsById(teamId);
        return vitalReportService.getVitalReport(reportId);
    }

    @Override
    public List<VitalMeasurement> getVitalMeasurements(UUID teamId) {
        return vitalMeasurementService.getMeasurements(teamId);
    }
}
