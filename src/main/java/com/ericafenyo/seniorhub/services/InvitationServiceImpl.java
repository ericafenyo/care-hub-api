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

import com.ericafenyo.seniorhub.EnvironmentVariables;
import com.ericafenyo.seniorhub.dto.AcceptInvitationRequest;
import com.ericafenyo.seniorhub.dto.InvitationRequest;
import com.ericafenyo.seniorhub.dto.ValidateInvitationRequest;
import com.ericafenyo.seniorhub.entities.CaretakerSeniorEntity;
import com.ericafenyo.seniorhub.entities.InvitationEntity;
import com.ericafenyo.seniorhub.exceptions.invitation.InvitationAlreadyUsedException;
import com.ericafenyo.seniorhub.exceptions.invitation.InvitationException;
import com.ericafenyo.seniorhub.exceptions.invitation.InvitationExpiredException;
import com.ericafenyo.seniorhub.exceptions.invitation.InvitationNotFoundException;
import com.ericafenyo.seniorhub.exceptions.invitation.InviteeNotFoundException;
import com.ericafenyo.seniorhub.exceptions.invitation.InviterNotFoundException;
import com.ericafenyo.seniorhub.exceptions.invitation.SeniorNotFoundException;
import com.ericafenyo.seniorhub.mapper.InvitationMapper;
import com.ericafenyo.seniorhub.model.Invitation;
import com.ericafenyo.seniorhub.model.Mail;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.repository.CaretakerSeniorRepository;
import com.ericafenyo.seniorhub.repository.InvitationRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.util.Hashing;
import com.ericafenyo.seniorhub.util.Invitations;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final CaretakerSeniorRepository caretakerSeniorRepository;
    private final EnvironmentVariables environment;
    private final InvitationMapper invitationMapper;
    private final InvitationRepository invitationRepository;
    private final MailService mailService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Report invite(InvitationRequest request) throws InvitationException {
        // Get the inviter
        var inviter = userRepository.findById(request.getInviterId());
        if (inviter.isEmpty()) {
            throw new InviterNotFoundException();
        }

        // Get the senior
        var senior = userRepository.findById(request.getSeniorId());
        if (senior.isEmpty()) {
            throw new SeniorNotFoundException();
        }

        String token = Hashing.randomSHA256();

        var invitation = new InvitationEntity()
                .setToken(token)
                .setEmail(request.getEmail())
                .setExpiresAt(Instant.now().plusSeconds(environment.getInvitationExpirySeconds()))
                .setInviter(inviter.get())
                .setSenior(senior.get());

        invitationRepository.save(invitation);

        var baseUrl = environment.getBaseUrl();
        var link = "%s?token=%s".formatted(baseUrl, token);
        var context = new Mail.Context();
        context.put("link", link);
        return mailService.sendInvitation(request.getEmail(), context);
    }

    @Override
    public Invitation validateInvitation(ValidateInvitationRequest request) throws InvitationException {
        var invitation = invitationRepository.findByToken(request.getToken())
                .orElseThrow(() -> new InvitationNotFoundException());

        if (Invitations.hasExpired(invitation.getExpiresAt())) {
            throw new InvitationExpiredException();
        }

        if (invitation.getUsedAt() != null) {
            throw new InvitationAlreadyUsedException();
        }

        return invitationMapper.apply(invitation);
    }

    @Override
    @Transactional
    public void acceptInvitation(AcceptInvitationRequest request) throws InvitationException {
        var invitation = invitationRepository.findByToken(request.getToken())
                .orElseThrow(() -> new InvitationNotFoundException());

        var invitee = userRepository.findByEmail(invitation.getEmail())
                .orElseThrow(() -> new InviteeNotFoundException());

        Optional.ofNullable(invitation.getInviter()).orElseThrow(() -> new InviterNotFoundException());

        var senior = Optional.ofNullable(invitation.getSenior())
                .orElseThrow(() -> new SeniorNotFoundException());


        var caretakerSenior = new CaretakerSeniorEntity()
                .setCaretaker(invitee)
                .setSenior(senior);

        // Save the caretakerSenior
        caretakerSeniorRepository.save(caretakerSenior);

        invitation.setUsedAt(Instant.now());
        invitation.setStatus(Invitation.Status.ACCEPTED);

        invitationRepository.save(invitation);
    }
}
