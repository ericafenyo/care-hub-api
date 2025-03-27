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
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

// Rules:
// 1. Membership cannot be deleted, only canceled.
// 2. Only pending memberships can be deleted.
// 3. Active memberships can be suspended.
// 4. Suspended memberships can be activated.
// 5. Canceled memberships cannot be activated.
// 6. Canceled memberships can be deleted.
// 7. Pending memberships can be approved or rejected.
// 8. Approved memberships can be activated.
// 9. Rejected memberships can be deleted.
// 10. A user can have only one active membership in a team.

/**
 * A membership represents a user's role and permissions in a team.
 */
@Data
@Accessors(chain = true)
public class Membership {
    /**
     * The unique identifier for the membership.
     */
    private UUID id;

    /**
     * The user associated with the membership.
     */
    private PartialUser user;

    /**
     * The team tied to the membership.
     */
    private Team team;

    /**
     * The role of the member in the team.
     */
    private Role role;

    /**
     * The date the membership was created.
     */

    private Instant createdAt;

    /**
     * The status of the membership.
     */
    private Status status;

    public enum Status {
        /**
         * The membership is currently active.
         */
        ACTIVE,

        /**
         * The membership has been temporarily suspended.
         */
        SUSPENDED,

        /**
         * The membership is waiting to be approved or activated.
         */
        PENDING,

        /**
         * The membership has not been approved.
         */
        REJECTED,

        /**
         * The membership has been canceled (either by user request, by admin or by system action).
         */
        CANCELLED,
    }

}
