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

package com.ericafenyo.seniorhub.entities;

import com.ericafenyo.seniorhub.model.Priority;
import com.ericafenyo.seniorhub.model.Task.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@Data
@Accessors(chain = true)
public class TaskEntity {

    /**
     * The unique identifier for the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * The title of the task.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * The short description of the task.
     */
    @Column(name = "description")
    private String description;

    /**
     * The current status of the task.
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.PLANNED;

    /**
     * The priority of the task.
     */
    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    /**
     * The date when the task will be due.
     */
    @Column(name = "due_date")
    private LocalDate dueDate;

    /**
     * The list of users assigned to the task.
     */
    @ManyToMany()
    @JoinTable(
        name = "task_user",
        joinColumns = @JoinColumn(name = "task_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    private List<UserEntity> assignees = new ArrayList<>();

    /**
     * The team to which the task belongs.
     */
    @ManyToOne()
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    /**
     * The recurrence pattern for the task.
     */
    @OneToOne()
    @JoinColumn(name = "recurrence_id")
    private RecurrenceEntity recurrence;

    /**  
     * The timestamp at which the task was created.
     */
    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    /**
     * The timestamp at which the task was last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;
}
