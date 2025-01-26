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

import com.ericafenyo.seniorhub.Messages;
import com.ericafenyo.seniorhub.contexts.CreateTaskContext;
import com.ericafenyo.seniorhub.core.AuthenticationContext;
import com.ericafenyo.seniorhub.dto.UpdateTeamRequest;
import com.ericafenyo.seniorhub.entities.TaskEntity;
import com.ericafenyo.seniorhub.entities.TeamEntity;
import com.ericafenyo.seniorhub.entities.MembershipEntity;
import com.ericafenyo.seniorhub.exceptions.ConflictException;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.exceptions.NotFoundException;
import com.ericafenyo.seniorhub.interactor.FindTeamByIdInteractor;
import com.ericafenyo.seniorhub.mapper.MembershipMapper;
import com.ericafenyo.seniorhub.mapper.TaskMapper;
import com.ericafenyo.seniorhub.mapper.TeamMapper;
import com.ericafenyo.seniorhub.model.Invitation;
import com.ericafenyo.seniorhub.model.Membership;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.model.Role;
import com.ericafenyo.seniorhub.model.Task;
import com.ericafenyo.seniorhub.model.Team;
import com.ericafenyo.seniorhub.repository.RoleRepository;
import com.ericafenyo.seniorhub.repository.TaskRepository;
import com.ericafenyo.seniorhub.repository.MembershipRepository;
import com.ericafenyo.seniorhub.repository.TeamRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.services.InvitationService;
import com.ericafenyo.seniorhub.services.TeamService;
import com.ericafenyo.seniorhub.services.validation.Validations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultTeamService extends AuthenticationContext implements TeamService {
    private final TeamMapper mapper;
    private final TaskMapper taskMapper;

    private final FindTeamByIdInteractor findTeamByIdInteractor;

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
}
