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

package com.ericafenyo.seniorhub.interactor;

import com.ericafenyo.seniorhub.Messages;
import com.ericafenyo.seniorhub.entities.TeamEntity;
import com.ericafenyo.seniorhub.exceptions.NotFoundException;
import com.ericafenyo.seniorhub.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindTeamByIdInteractor extends Interactor<UUID, TeamEntity> {
    private final TeamRepository repository;
    private final Messages messages;

    @Override
    protected TeamEntity create(UUID params) throws Exception {
        return repository.findById(params)
                .orElseThrow(() -> new NotFoundException(
                                messages.format("error.resource.not.found", "Team"),
                                messages.format("error.resource.not.found.code", "team")
                        )
                );
    }
}
