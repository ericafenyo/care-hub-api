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

package com.ericafenyo.seniorhub.controllers;

import com.ericafenyo.seniorhub.dto.AcceptInvitationRequest;
import com.ericafenyo.seniorhub.dto.InvitationRequest;
import com.ericafenyo.seniorhub.dto.ValidateInvitationRequest;
import com.ericafenyo.seniorhub.exceptions.invitation.InvitationException;
import com.ericafenyo.seniorhub.model.Invitation;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.services.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService service;

    @PostMapping()
    public Report validateInvitation(@RequestBody @Valid InvitationRequest request) throws InvitationException {
        return service.invite(request);
    }

    @PostMapping("/validate")
    public Invitation validateInvitation(@RequestBody @Valid ValidateInvitationRequest request) throws InvitationException {
        return service.validateInvitation(request);
    }

    @PostMapping("/accept")
    public void acceptInvitation(@RequestBody @Valid AcceptInvitationRequest request) throws InvitationException {
        service.acceptInvitation(request);
    }
}
