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

package com.ericafenyo.carehub.controllers;

import com.ericafenyo.carehub.dto.AcceptInvitationRequest;
import com.ericafenyo.carehub.dto.ValidateInvitationRequest;
import com.ericafenyo.carehub.exceptions.DomainException;
import com.ericafenyo.carehub.model.Invitation;
import com.ericafenyo.carehub.model.Report;
import com.ericafenyo.carehub.services.InvitationService;
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

    @PostMapping("/validate")
    public Invitation validateInvitation(@RequestBody @Valid ValidateInvitationRequest request) throws DomainException {
        return service.validateInvitation(request);
    }

    @PostMapping("/accept")
    public Report acceptInvitation(@RequestBody @Valid AcceptInvitationRequest request) throws DomainException {
        return service.acceptInvitation(request);
    }
}
