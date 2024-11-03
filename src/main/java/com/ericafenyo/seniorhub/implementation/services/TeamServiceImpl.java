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
import com.ericafenyo.seniorhub.contexts.CreateNoteContext;
import com.ericafenyo.seniorhub.contexts.CreateTaskContext;
import com.ericafenyo.seniorhub.dto.CreateTeamRequest;
import com.ericafenyo.seniorhub.dto.UpdateTeamRequest;
import com.ericafenyo.seniorhub.entities.NoteEntity;
import com.ericafenyo.seniorhub.entities.TaskEntity;
import com.ericafenyo.seniorhub.entities.TeamEntity;
import com.ericafenyo.seniorhub.exceptions.ConflictException;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.exceptions.NotFoundException;
import com.ericafenyo.seniorhub.mapper.NoteMapper;
import com.ericafenyo.seniorhub.mapper.TaskMapper;
import com.ericafenyo.seniorhub.mapper.TeamMapper;
import com.ericafenyo.seniorhub.model.Invitation;
import com.ericafenyo.seniorhub.model.Note;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.model.Task;
import com.ericafenyo.seniorhub.model.Team;
import com.ericafenyo.seniorhub.repository.NoteRepository;
import com.ericafenyo.seniorhub.repository.TaskRepository;
import com.ericafenyo.seniorhub.repository.TeamRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.services.InvitationService;
import com.ericafenyo.seniorhub.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamMapper mapper;
    private final TaskMapper taskMapper;
    private final NoteMapper toNote;

    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    private final InvitationService invitationService;
    private final Messages messages;

    @Override
    public Team createTeam(
        CreateTeamRequest request,
        String creatorId
    ) throws HttpException {
        var exists = teamRepository.existsByName(request.getName());

        if (exists) {
            throw new ConflictException("Team with name " + request.getName() + " already exists", "not-found");
        }

        var creator = userRepository.findById(creatorId)
            .orElseThrow(() -> new NotFoundException("Creator with id " + creatorId + " not found", "creator-not-found"));

        var team = new TeamEntity();
        team.setName(request.getName());
        team.setDescription(request.getDescription());
        team.setCreator(creator);
        return mapper.apply(teamRepository.save(team));
    }

    @Override
    public List<Team> getTeams() {
        return teamRepository.findAll().stream()
            .map(mapper)
            .toList();
    }

    @Override
    public Team getTeamById(String id) throws HttpException {
        var team = teamRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Team with id not found", ""));

        return mapper.apply(team);
    }

    @Override
    public Team updateTeam(String id, UpdateTeamRequest userUpdateDto) {
        var team = teamRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        team.setName(userUpdateDto.getName());
        team.setDescription(userUpdateDto.getDescription());

        return mapper.apply(teamRepository.save(team));
    }

    @Override
    public void deleteTeam(String id) {
    }


    @Override
    public List<Team> getUserTeams(Long id) throws HttpException {
        return teamRepository.findAllByCreatorId(id)
            .stream()
            .map(mapper)
            .toList();
    }

    @Override
    public Report invite(String teamId, String inviterId, String role, String email) throws HttpException {
        return invitationService.invite(teamId, inviterId, role, email);
    }

    @Override
    public Invitation validateInvitation(String id) {
        return null;
    }

    @Override
    public Task createTask(CreateTaskContext context) throws HttpException {
        var team = teamRepository.findById(context.getTeamId()).orElseThrow(() ->
            new NotFoundException(
                messages.format(Messages.ERROR_RESOURCE_WITH_ID_NOTFOUND, "Team", context.getTeamId()),
                messages.format(Messages.ERROR_RESOURCE_NOTFOUND_CODE, "team")
            )
        );

        var entity = new TaskEntity()
            .setTitle(context.getTitle())
            .setDescription(context.getDescription())
            .setDueDate(context.getDueDate())
            .setPriority(context.getPriority())
            .setTeam(team);

        TaskEntity task = taskRepository.save(entity);

        return taskMapper.apply(task);
    }

    @Override
    public Note createNote(CreateNoteContext context) throws HttpException {
        var team = findById(context.getTeamId());
        var author = userRepository.findById(context.getUserId()).orElseThrow(() ->
            new NotFoundException(
                messages.format(Messages.ERROR_RESOURCE_WITH_ID_NOTFOUND, "User", context.getTeamId()),
                messages.format(Messages.ERROR_RESOURCE_NOTFOUND_CODE, "user")
            )
        );

        var entity = new NoteEntity()
            .setTitle(context.getTitle())
            .setContent(context.getContent())
            .setTeam(team)
            .setAuthor(author);

        return noteRepository.save(entity).map(toNote);
    }

    @Override
    public List<Note> getNotes(String teamId, String userId) {
        return noteRepository.findByTeamId(teamId)
            .stream()
            .map(toNote)
            .toList();
    }

    private TeamEntity findById(String teamId) throws NotFoundException {
        return teamRepository.findById(teamId).orElseThrow(() ->
            new NotFoundException(
                messages.format(Messages.ERROR_RESOURCE_WITH_ID_NOTFOUND, "Team", teamId),
                messages.format(Messages.ERROR_RESOURCE_NOTFOUND_CODE, "team")
            )
        );
    }
}
