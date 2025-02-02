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

package com.ericafenyo.carehub.services;

import com.ericafenyo.carehub.dto.AcceptInvitationRequest;
import com.ericafenyo.carehub.dto.ValidateInvitationRequest;
import com.ericafenyo.carehub.exceptions.HttpException;
import com.ericafenyo.carehub.model.Invitation;
import com.ericafenyo.carehub.model.Report;

import java.util.UUID;

/**
 * This interface defines the contract for invitation-related operations.
 */
public interface InvitationService {
    /**
     * Invites a user to join a team using the provided information.
     *
     * @param teamId    The ID of the team to which the user is being invited.
     * @param role      The role to be assigned to the user.
     * @param firstName The first name of the user being invited.
     * @param lastName  The last name of the user being invited.
     * @param email     The email address of the user being invited.
     * @return A {@link Report} object containing the result of the invitation.
     * @throws HttpException If the invitation fails, an InvitationException is thrown.
     */
    Report invite(UUID teamId, String role, String firstName, String lastName, String email) throws HttpException;

    /**
     * Validates an invitation using the provided request.
     *
     * @param request The request object containing the necessary information to validate an invitation.
     * @return An  {@link Invitation} object if the validation is successful.
     * @throws HttpException If the validation fails, an InvitationException is thrown.
     */
    Invitation validateInvitation(ValidateInvitationRequest request) throws HttpException;

    /**
     * Accepts an invitation using the provided request.
     *
     * @param request The request object containing the necessary information to accept an invitation.
     * @throws HttpException If the acceptance fails, an InvitationException is thrown.
     */
    Report acceptInvitation(AcceptInvitationRequest request) throws HttpException;
}
