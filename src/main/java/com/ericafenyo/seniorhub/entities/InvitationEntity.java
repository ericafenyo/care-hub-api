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
 * A database entity representing an invitation to add a caretaker to a senior account.
 */
@Entity(name = "invitations")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @Accessors(chain = true) @ToString
public class InvitationEntity {
    /**
     * The unique identifier for the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The universally unique identifier for the entity.
     * <p>
     * This is the actual id revealed publicly, the primary id is kept internally.
     */
    @Column(name = "uuid", unique = true)
    private String uuid = UUID.randomUUID().toString();

    /**
     * The token associated with the invitation
     */
    @Column(name = "token", unique = true)
    private String token;

    /**
     * The email address of the invitation recipient
     */
    @Column(name = "email")
    private String email;

    /**
     * The status of the invitation
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Invitation.Status status = Invitation.Status.PENDING;

    /**
     * The date and time when the invitation was created
     */
    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    /**
     * The date and time when the invitation was last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * The timestamp indicating when the invitation expires
     */
    @Column(name = "expires_at")
    private Instant expiresAt;

    /**
     * The timestamp indicating when the invitation was used
     */
    @Column(name = "used_at")
    private Instant usedAt;

    /**
     * The senior account to which the caretaker will be added
     */
    @ManyToOne()
    @JoinColumn(name = "senior_id")
    private UserEntity senior;

    /**
     * The user who sent the invitation
     */
    @ManyToOne()
    @JoinColumn(name = "inviter_id")
    private UserEntity inviter;
}
