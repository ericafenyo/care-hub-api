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

package com.ericafenyo.carehub.controllers;

import com.ericafenyo.carehub.exceptions.HttpException;
import com.ericafenyo.carehub.model.Note;
import com.ericafenyo.carehub.requests.CreateNoteRequest;
import com.ericafenyo.carehub.requests.UpdateNoteRequest;
import com.ericafenyo.carehub.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class NoteController {
    private final NoteService service;

    // Notes sub-resources
    @PostMapping("/teams/{teamId}/notes")
    public Note createNote(
        @PathVariable("teamId") UUID teamId,
        @RequestBody CreateNoteRequest request
    ) throws HttpException {
        return service.createNote(request.getTitle(), request.getContent(), teamId);
    }

    @GetMapping("/teams/{teamId}/notes")
    public List<Note> getNotes(
        @PathVariable("teamId") UUID teamId,
        Authentication authentication
    ) throws HttpException {
        return service.getNotes(teamId);
    }

    // Get note by id
    @GetMapping("/teams/{teamId}/notes/{noteId}")
    public Note getNoteById(@PathVariable() UUID teamId, @PathVariable() UUID noteId) throws HttpException {
        return service.getNote(teamId, noteId);
    }

    // Update note
    @PatchMapping("/teams/{teamId}/notes/{noteId}")
    public Note updateNote(
        @PathVariable("teamId") UUID teamId,
        @PathVariable("noteId") UUID noteId,
        @RequestBody() UpdateNoteRequest request
    ) throws HttpException {
        return service.updateNote(teamId, noteId, request.getTitle(), request.getContent());
    }

    // Delete a note by id
    @DeleteMapping("/teams/{teamId}/notes/{noteId}")
    public void deleteNote(@PathVariable() UUID teamId, @PathVariable() UUID noteId) throws HttpException {
        service.deleteNote(teamId, noteId);
    }
}
