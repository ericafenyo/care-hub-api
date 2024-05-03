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

package com.ericafenyo.seniorhub.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents an invitation to add a caretaker to a senior account.
 */
@Getter
@Setter
public class Invitation {
    /**
     * The unique identifier of the invitation
     */
    private String id;

    /**
     * The token associated with the invitation
     */
    private String token;

    /**
     * The email address of the recipient
     */
    private String email;

    /**
     * Role to be assigned to the recipient upon acceptance
     */
    private String role;

    /**
     * The status of the invitation
     */
    private Status status;

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
     * The senior account to which the caretaker will be added
     */
    private User senior;

    /**
     * The status of an invitation
     */
    public enum Status {PENDING, ACCEPTED}
}
