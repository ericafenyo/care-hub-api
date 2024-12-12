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

package com.ericafenyo.seniorhub.impl.services;

import com.ericafenyo.seniorhub.Messages;
import com.ericafenyo.seniorhub.core.AuthenticationContext;
import com.ericafenyo.seniorhub.entities.MedicationEntity;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.exceptions.NotFoundException;
import com.ericafenyo.seniorhub.mapper.MedicationMapper;
import com.ericafenyo.seniorhub.model.Medication;
import com.ericafenyo.seniorhub.repository.MedicationRepository;
import com.ericafenyo.seniorhub.repository.TeamRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.services.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl extends AuthenticationContext implements MedicationService {
  private final MedicationRepository medicationRepository;
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;

  private final Messages messages;

  private final MedicationMapper toMedication;

  @Override
  public Medication addMedication(
      String name,
      String dosage,
      String frequency,
      String route,
      String instructions,
      LocalDate startDate,
      LocalDate endDate,
      UUID teamId
  ) throws HttpException {
    var team = teamRepository.findById(teamId).orElseThrow(() ->
        new NotFoundException(
            messages.format(Messages.ERROR_RESOURCE_WITH_ID_NOTFOUND, "Team", teamId),
            messages.format(Messages.ERROR_RESOURCE_NOTFOUND_CODE, "team")
        )
    );

    var user = userRepository.findById(getAuthenticatedUserId()).orElseThrow(() ->
        new NotFoundException(
            messages.format(Messages.ERROR_RESOURCE_NOTFOUND, "User"),
            messages.format(Messages.ERROR_RESOURCE_NOTFOUND_CODE, "user")
        )
    );

    var entity = new MedicationEntity()
        .setName(name)
        .setDosage(dosage)
        .setFrequency(frequency)
        .setRoute(route)
        .setInstructions(instructions)
        .setStartDate(startDate)
        .setEndDate(endDate)
        .setTeam(team)
        .setUser(user);

    return medicationRepository.save(entity).map(toMedication);
  }

  @Override
  public List<Medication> getMedications(UUID teamId) {
    return medicationRepository.findByTeamIdAndUserId(teamId, getAuthenticatedUserId())
        .stream()
        .map(toMedication)
        .toList();
  }

  @Override
  public Medication getMedication(UUID teamId, UUID MedicationId) throws HttpException {
    return null;
  }

  @Override
  public Medication updateMedication(UUID teamId, UUID MedicationId, String title, String content) throws HttpException {
    return null;
  }

  @Override
  public void deleteMedication(UUID teamId, UUID MedicationId) throws HttpException {

  }
}
