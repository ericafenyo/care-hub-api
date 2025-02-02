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

package com.ericafenyo.carehub.entities;

import com.ericafenyo.carehub.api.Mappable;
import com.ericafenyo.carehub.model.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;

@Entity(name = "teams")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public class TeamEntity implements Mappable<TeamEntity, Team> {
    /**
     * The unique identifier for the team.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * The name of the team.
     */
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    /**
     * A brief description of the team.
     */
    @Column(name = "description", nullable = false, length = 150)
    private String description;

    /**
     * The timestamp indicating when the team was created.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /**
     * The timestamp indicating when the team was last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * The creator of the team.
     */
    @ManyToOne()
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @Override
    public Team map(Function<? super TeamEntity, ? extends Team> mapper) {
        return mapper.apply(this);
    }
}
