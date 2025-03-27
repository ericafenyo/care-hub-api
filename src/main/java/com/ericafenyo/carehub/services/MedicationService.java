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

import com.ericafenyo.carehub.exceptions.DomainException;
import com.ericafenyo.carehub.model.Medication;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing medications.
 */
public interface MedicationService {
    Medication addMedication(
        String name,
        String dosage,
        String frequency,
        String route,
        String instructions,
        LocalDate startDate,
        LocalDate endDate,
        UUID teamId
    ) throws DomainException;

    List<Medication> getMedications(UUID teamId) throws DomainException;

    Medication getMedication(UUID teamId, UUID MedicationId) throws DomainException;

    Medication updateMedication(UUID teamId, UUID MedicationId, String title, String content) throws DomainException;

    void deleteMedication(UUID teamId, UUID MedicationId) throws DomainException;
}
