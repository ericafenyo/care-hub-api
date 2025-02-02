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

package com.ericafenyo.carehub.mapper;

import com.ericafenyo.carehub.entities.TaskEntity;
import com.ericafenyo.carehub.model.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Maps a {@link TaskMapper} to a {@link Task}.
 */
@Component
@RequiredArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class TaskMapper implements Function<TaskEntity, Task> {

    /**
     * Converts a {@link TaskEntity} to a {@link Task}.
     *
     * @param entity The input task entity.
     * @return The mapped task.
     */
    @Override
    public Task apply(TaskEntity entity) {
        var task = new Task();
        task.setId(entity.getId());
        task.setTitle(entity.getTitle());
        task.setDescription(entity.getDescription());
        task.setCreatedAt(entity.getCreatedAt());
        task.setUpdatedAt(entity.getUpdatedAt());
        task.setStatus(entity.getStatus());
        task.setDueDate(entity.getDueDate());
        task.setPriority(entity.getPriority());
        return task;
    }
}
