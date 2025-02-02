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

package com.ericafenyo.carehub.services;

import com.ericafenyo.carehub.exceptions.HttpException;
import com.ericafenyo.carehub.model.Note;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing notes.
 */
public interface NoteService {

    /**
     * Creates a new note within a specified team.
     *
     * @param title   The title of the note.
     * @param content The content of the note.
     * @param teamId  The ID of the team to which the note belongs.
     * @return The created Note object.
     * @throws HttpException if there is an error during the note creation process.
     */
    Note createNote(String title, String content, UUID teamId) throws HttpException;

    /**
     * Retrieves all notes associated with a specified team.
     *
     * @param teamId The ID of the team whose notes are to be retrieved.
     * @return A list of Note objects for the specified team.
     * @throws HttpException if there is an error during the retrieval of notes.
     */
    List<Note> getNotes(UUID teamId) throws HttpException;

    /**
     * Retrieves a specific note within a team by its unique ID.
     *
     * @param teamId The ID of the team to which the note belongs.
     * @param noteId The unique ID of the note to retrieve.
     * @return The Note object corresponding to the specified note ID.
     * @throws HttpException if the note cannot be found or if an error occurs during retrieval.
     */
    Note getNote(UUID teamId, UUID noteId) throws HttpException;

    /**
     * Updates an existing note within a specified team.
     *
     * @param teamId  The ID of the team to which the note belongs.
     * @param noteId  The unique ID of the note to update.
     * @param title   The updated title of the note.
     * @param content The updated content of the note.
     * @return The updated Note object.
     * @throws HttpException if an error occurs during the update process.
     */
    Note updateNote(UUID teamId, UUID noteId, String title, String content) throws HttpException;

    /**
     * Deletes a specific note within a team.
     *
     * @param teamId The ID of the team to which the note belongs.
     * @param noteId The unique ID of the note to delete.
     * @throws HttpException if the note cannot be found or if an error occurs during deletion.
     */
    void deleteNote(UUID teamId, UUID noteId) throws HttpException;
}
