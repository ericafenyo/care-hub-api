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

import com.ericafenyo.carehub.exceptions.HttpException;
import com.ericafenyo.carehub.model.Medication;
import com.ericafenyo.carehub.requests.CreateMedicationRequest;
import com.ericafenyo.carehub.requests.UpdateMedicationRequest;
import com.ericafenyo.carehub.services.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MedicationController {
  private final MedicationService service;

  @PostMapping("/teams/{teamId}/medications")
  public Medication addMedication(
      @PathVariable("teamId") UUID teamId,
      @RequestBody CreateMedicationRequest request
  ) throws HttpException {
    return service.addMedication(
        request.getName(),
        request.getDosage(),
        request.getFrequency(),
        request.getRoute(),
        request.getInstructions(),
        request.getStartDate(),
        request.getEndDate(),
        teamId
    );
  }

  @GetMapping("/teams/{teamId}/medications")
  public List<Medication> getMedications(
      @PathVariable("teamId") UUID teamId,
      Authentication authentication
  ) throws HttpException {
    return service.getMedications(teamId);
  }

  // Get medication by id
  @GetMapping("/teams/{teamId}/medications/{medicationId}")
  public Medication getMedicationById(@PathVariable() UUID teamId, @PathVariable() UUID medicationId) throws HttpException {
    return service.getMedication(teamId, medicationId);
  }

  // Update medication
  @PatchMapping("/teams/{teamId}/medications/{medicationId}")
  public Medication updateMedication(
      @PathVariable("teamId") UUID teamId,
      @PathVariable("medicationId") UUID medicationId,
      @RequestBody() UpdateMedicationRequest request
  ) throws HttpException {
//        return service.updateMedication(teamId, medicationId, request.getTitle(), request.getContent());
    return null;
  }

  // Delete a medication by id
  @DeleteMapping("/teams/{teamId}/medications/{medicationId}")
  public void deleteMedication(@PathVariable() UUID teamId, @PathVariable() UUID medicationId) throws HttpException {
    service.deleteMedication(teamId, medicationId);
  }
}
