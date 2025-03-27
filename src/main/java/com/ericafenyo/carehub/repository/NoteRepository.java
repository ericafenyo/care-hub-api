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

package com.ericafenyo.carehub.repository;

import com.ericafenyo.carehub.entities.NoteEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for performing database operations on a {@link NoteEntity}.
 */
@Repository
public interface NoteRepository extends AbstractRepository<NoteEntity> {
    /**
     * Retrieves a list of notes for a given team id.
     *
     * @param teamId The unique identifier of the team.
     * @return A list of notes for the given team id or an empty list if none found.
     */
    List<NoteEntity> findByTeamId(UUID teamId);

    /**
     * Retrieves a note for a given note id, team id and author id.
     *
     * @param id       The unique identifier of the note.
     * @param teamId   The unique identifier of the team.
     * @param authorId The unique identifier of the author.
     * @return A note for the given note id, team id and author id or an empty optional if none found.
     */
    Optional<NoteEntity> findByIdAndTeamIdAndAuthorId(UUID id, UUID teamId, UUID authorId);
}
