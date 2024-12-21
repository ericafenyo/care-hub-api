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

import com.ericafenyo.seniorhub.model.Invitation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

/**
 * A database entity representing an invitation to add a member to a team.
 */
@Entity(name = "invitations")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @ToString @Accessors(chain = true)
public class InvitationEntity {
    /**
     * The unique identifier for the invitation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * The first name of the invitation recipient
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The last name of the invitation recipient
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * The email address of the invitation recipient
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * The status of the invitation
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Invitation.Status status;

    /**
     * The token associated with the invitation
     */
    @Column(name = "token", unique = true, nullable = false)
    private String token;

    /**
     * The role that will be assigned to the member upon acceptance
     */
    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    /**
     * The user who sent the invitation
     */
    @ManyToOne()
    @JoinColumn(name = "inviter_id", nullable = false)
    private UserEntity inviter;

    /**
     * The team to which the member will be added
     */
    @ManyToOne()
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    /**
     * The timestamp indicating when the invitation should expire
     */
    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    /**
     * The timestamp indicating when the invitation was used
     */
    @Column(name = "used_at")
    private Instant usedAt;

    /**
     * The timestamp indicating when the invitation was created
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /**
     * The timestamp indicating when the invitation was last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
