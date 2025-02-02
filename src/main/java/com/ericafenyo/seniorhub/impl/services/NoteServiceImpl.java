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

package com.ericafenyo.seniorhub.impl.services;

import com.ericafenyo.seniorhub.Messages;
import com.ericafenyo.seniorhub.core.AuthenticationContext;
import com.ericafenyo.seniorhub.entities.NoteEntity;
import com.ericafenyo.seniorhub.entities.UserEntity;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.exceptions.NotFoundException;
import com.ericafenyo.seniorhub.mapper.NoteMapper;
import com.ericafenyo.seniorhub.model.Note;
import com.ericafenyo.seniorhub.repository.NoteRepository;
import com.ericafenyo.seniorhub.repository.TeamRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.services.NoteService;
import com.ericafenyo.seniorhub.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl extends AuthenticationContext implements NoteService {
    private final NoteRepository noteRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    private final TeamService teamService;

    private final Messages messages;

    private final NoteMapper toNote;

    @Override
    public Note createNote(String title, String content, UUID teamId) throws HttpException {
        var userId = getAuthenticatedUserId();

        var team = teamRepository.findById(teamId).get();

        var author = userRepository.findById(userId).get();

        var entity = new NoteEntity()
                .setTitle(title)
                .setContent(content)
                .setTeam(team)
                .setAuthor(author);

        return noteRepository.save(entity).map(toNote);
    }


    @Override
    public List<Note> getNotes(UUID teamId) {
        return noteRepository.findByTeamId(teamId)
                .stream()
                .map(toNote)
                .toList();
    }

    @Override
    public Note getNote(UUID teamId, UUID noteId) throws HttpException {
        var author = getAuthenticatedUser();

        var note = noteRepository.findByIdAndTeamIdAndAuthorId(noteId, teamId, author.getId()).get();

        return note.map(toNote);
    }

    @Override
    public Note updateNote(UUID teamId, UUID noteId, String title, String content) throws HttpException {
        var author = getAuthenticatedUser();

        var note = noteRepository.findByIdAndTeamIdAndAuthorId(noteId, teamId, author.getId()).get();

        note.setTitle(title);
        note.setContent(content);

        return noteRepository.save(note).map(toNote);
    }

    @Override
    public void deleteNote(UUID noteId, UUID teamId) throws HttpException {
        var author = getAuthenticatedUser();
        noteRepository.findByIdAndTeamIdAndAuthorId(teamId, noteId, author.getId())
                .ifPresent(note -> noteRepository.delete(note));
    }

    private UserEntity getAuthenticatedUser() throws NotFoundException {
        var userId = getAuthenticatedUserId();

        return userRepository.findById(userId).get();
    }
}
