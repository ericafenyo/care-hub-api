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

package com.ericafenyo.carehub.domain.service.impl;

import com.ericafenyo.carehub.core.AuthenticationContext;
import com.ericafenyo.carehub.domain.service.rule.HasMembershipRule;
import com.ericafenyo.carehub.entities.NoteEntity;
import com.ericafenyo.carehub.entities.UserEntity;
import com.ericafenyo.carehub.exceptions.DomainException;
import com.ericafenyo.carehub.exceptions.NotFoundException;
import com.ericafenyo.carehub.mapper.NoteMapper;
import com.ericafenyo.carehub.model.Note;
import com.ericafenyo.carehub.repository.NoteRepository;
import com.ericafenyo.carehub.repository.TeamRepository;
import com.ericafenyo.carehub.repository.UserRepository;
import com.ericafenyo.carehub.services.NoteService;
import com.ericafenyo.carehub.services.validation.Validations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl extends AuthenticationContext implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final NoteMapper mapper;

    private final HasMembershipRule hasMembershipRule;
    private final Validations validations;

    @Override
    public Note createNote(String title, String content, UUID teamId) throws DomainException {
        var userId = getAuthenticatedUserId();

        // Validate that the team and user should exist
        validations.validateTeamShouldExists(teamId);
        validations.validateUserShouldExists(userId);

        // Validate that the user has a membership in the team
        hasMembershipRule.validate(new HasMembershipRule.Params(teamId, userId));

        var team = teamRepository.findById(teamId).get();
        var author = userRepository.findById(userId).get();

        var entity = new NoteEntity()
                .setTitle(title)
                .setContent(content)
                .setTeam(team)
                .setAuthor(author);

        return noteRepository.save(entity).map(mapper);
    }


    @Override
    public List<Note> getNotes(UUID teamId) {
        return noteRepository.findByTeamId(teamId)
                .stream()
                .map(mapper)
                .toList();
    }

    @Override
    public Note getNote(UUID teamId, UUID noteId) throws DomainException {
        var author = getAuthenticatedUser();

        var note = noteRepository.findByIdAndTeamIdAndAuthorId(noteId, teamId, author.getId()).get();

        return note.map(mapper);
    }

    @Override
    public Note updateNote(UUID teamId, UUID noteId, String title, String content) throws DomainException {
        var author = getAuthenticatedUser();

        var note = noteRepository.findByIdAndTeamIdAndAuthorId(noteId, teamId, author.getId()).get();

        note.setTitle(title);
        note.setContent(content);

        return noteRepository.save(note).map(mapper);
    }

    @Override
    public void deleteNote(UUID noteId, UUID teamId) throws DomainException {
        var author = getAuthenticatedUser();
        noteRepository.findByIdAndTeamIdAndAuthorId(teamId, noteId, author.getId())
                .ifPresent(note -> noteRepository.delete(note));
    }

    private UserEntity getAuthenticatedUser() throws NotFoundException {
        var userId = getAuthenticatedUserId();

        return userRepository.findById(userId).get();
    }
}
