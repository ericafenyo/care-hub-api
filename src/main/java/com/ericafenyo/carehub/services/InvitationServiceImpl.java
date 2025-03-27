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

import com.ericafenyo.carehub.EnvironmentVariables;
import com.ericafenyo.carehub.Messages;
import com.ericafenyo.carehub.core.AuthenticationContext;
import com.ericafenyo.carehub.dto.AcceptInvitationRequest;
import com.ericafenyo.carehub.dto.ValidateInvitationRequest;
import com.ericafenyo.carehub.entities.InvitationEntity;
import com.ericafenyo.carehub.exceptions.DomainException;
import com.ericafenyo.carehub.exceptions.NotFoundException;
import com.ericafenyo.carehub.exceptions.invitation.InvitationAlreadyUsedException;
import com.ericafenyo.carehub.exceptions.invitation.InvitationExpiredException;
import com.ericafenyo.carehub.exceptions.invitation.InvitationNotFoundException;
import com.ericafenyo.carehub.exceptions.role.InvalidRoleException;
import com.ericafenyo.carehub.mapper.InvitationMapper;
import com.ericafenyo.carehub.model.Invitation;
import com.ericafenyo.carehub.model.Mail;
import com.ericafenyo.carehub.model.Report;
import com.ericafenyo.carehub.repository.InvitationRepository;
import com.ericafenyo.carehub.repository.RoleRepository;
import com.ericafenyo.carehub.repository.TeamRepository;
import com.ericafenyo.carehub.repository.UserRepository;
import com.ericafenyo.carehub.util.Hashing;
import com.ericafenyo.carehub.util.Invitations;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl extends AuthenticationContext implements InvitationService {
    private final EnvironmentVariables environment;
    private final InvitationMapper invitationMapper;
    private final InvitationRepository invitationRepository;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final Messages messages;
    private final TeamRepository teamRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Report invite(
            UUID teamId,
            String role,
            String firstName,
            String lastName,
            String email
    ) throws DomainException {
        // inviter = select owner from teams where id = teamId and owner.id = authenticatedUserId
        // Get the inviter or throw an error if it does not exist
        var inviter = userRepository.findById(getAuthenticatedUserId()).orElseThrow(
                () -> new NotFoundException(
                        messages.format("error.resource.not.found", "User"),
                        messages.format("error.resource.not.found.code", "user")
                )
        );

        // Get the team or throw an error if it does not exist
        var team = teamRepository.findById(teamId).orElseThrow(() -> new NotFoundException(
                messages.format("error.resource.not.found", "Team", teamId),
                messages.format("error.resource.not.found.code", "team")
        ));

        var s = roleRepository.findBySlug(role).orElseThrow(() -> new InvalidRoleException());

        String token = Hashing.randomSHA256();


        var invitation = new InvitationEntity()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setToken(token)
                .setStatus(Invitation.Status.PENDING)
                .setRole(s)
                .setExpiresAt(Instant.now().plusSeconds(environment.getInvitationExpirySeconds()))
                .setTeam(team)
                .setInviter(inviter);

        invitationRepository.save(invitation);

        var baseUrl = environment.getBaseUrl();
        var link = "%s/invitations?token=%s".formatted(baseUrl, token);
        var context = new Mail.Context();
        context.put("link", link);
        return mailService.sendInvitation(email, context);
    }

    @Override
    public Invitation validateInvitation(ValidateInvitationRequest request) throws DomainException {
        var invitation = invitationRepository.findByToken(request.getToken())
                .orElseThrow(() -> new InvitationNotFoundException());

        // Check if the invitation has already been accepted
        if (invitation.getStatus() == Invitation.Status.ACCEPTED) {
            throw new InvitationAlreadyUsedException();
        }

        // Check if the invitation has expired
        if (Invitations.hasExpired(invitation.getExpiresAt())) {
            throw new InvitationExpiredException();
        }

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
    public Report acceptInvitation(AcceptInvitationRequest request) throws DomainException {
        // Check if the invitation exists
        var invitation = invitationRepository.findByToken(request.getToken())
                .orElseThrow(() -> new NotFoundException(
                        messages.format("error.resource.not.found", "invitation"),
                        messages.format("error.resource.not.found.code", "invitation")
                ));

        // Check if the invitation has already been accepted
        if (invitation.getStatus() == Invitation.Status.ACCEPTED) {
            throw new InvitationAlreadyUsedException();
        }

        // Check if the invitation has expired
        if (Invitations.hasExpired(invitation.getExpiresAt())) {
            throw new InvitationExpiredException();
        }

        var invitee = userRepository.findByEmail(invitation.getEmail())
                .orElseThrow(() -> new NotFoundException(
                        messages.format("error.resource.not.found", "Invitee"),
                        messages.format("error.resource.not.found.code", "invitee")
                ));

        var team = invitation.getTeam();

//        team.getMembers().add(invitee);

        invitation.setUsedAt(Instant.now());
        invitation.setStatus(Invitation.Status.ACCEPTED);

        invitationRepository.save(invitation);

        return new Report(messages.get("message.invitation.accepted"));
    }
}
