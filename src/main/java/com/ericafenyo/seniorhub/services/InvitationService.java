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

package com.ericafenyo.seniorhub.services;

import com.ericafenyo.seniorhub.dto.AcceptInvitationRequest;
import com.ericafenyo.seniorhub.dto.InvitationRequest;
import com.ericafenyo.seniorhub.dto.ValidateInvitationRequest;
import com.ericafenyo.seniorhub.exceptions.invitation.InvitationException;
import com.ericafenyo.seniorhub.model.Invitation;
import com.ericafenyo.seniorhub.model.Report;

/**
 * This interface defines the contract for invitation-related operations.
 */
public interface InvitationService {
    /**
     * Invites a person using the provided request.
     *
     * @param request The request object containing the necessary information to invite a person.
     * @return A {@link Report} object if the invitation is successful.
     * @throws InvitationException If the invitation fails, an InvitationException is thrown.
     */
    Report invite(InvitationRequest request) throws InvitationException;

    /**
     * Validates an invitation using the provided request.
     *
     * @param request The request object containing the necessary information to validate an invitation.
     * @return An  {@link Invitation} object if the validation is successful.
     * @throws InvitationException If the validation fails, an InvitationException is thrown.
     */
    Invitation validateInvitation(ValidateInvitationRequest request) throws InvitationException;

    /**
     * Accepts an invitation using the provided request.
     *
     * @param request The request object containing the necessary information to accept an invitation.
     * @throws InvitationException If the acceptance fails, an InvitationException is thrown.
     */
    void acceptInvitation(AcceptInvitationRequest request) throws InvitationException;
}