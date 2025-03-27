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

package com.ericafenyo.carehub.model;

import com.ericafenyo.carehub.domain.model.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents an invitation to add a member to a care team.
 */
@Getter
@Setter
@Accessors(chain = true)
public class Invitation {
    /**
     * The unique identifier of the invitation
     */
    private UUID id;

    /**
     * The email address of the person being invited
     */
    private String email;

    /**
     * The token used to validate the invitation
     */
    private String token;

    /**
     * The role that will be assigned to the member upon acceptance
     */
    private Role role;

    /**
     * The status of the invitation
     */
    private Status status;

    /**
     * The team to which the member will be added
     */
    private Team team;

    /**
     * The date and time indicating when the invitation was created
     */
    private Instant createdAt;

    /**
     * The timestamp indicating when the invitation expires
     */
    private Instant expiresAt;

    /**
     * The timestamp indicating when the invitation was used
     */
    private Instant usedAt;

    /**
     * The status of an invitation
     */
    public enum Status {PENDING, ACCEPTED, INVALIDATED, DECLINED}
}
