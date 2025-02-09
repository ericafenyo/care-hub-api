/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2025 Eric Afenyo
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

package com.ericafenyo.carehub.services.impl;

import com.ericafenyo.carehub.dto.CreateVitalReportRequest;
import com.ericafenyo.carehub.entities.TeamEntity;
import com.ericafenyo.carehub.entities.UserEntity;
import com.ericafenyo.carehub.entities.VitalMeasurementEntity;
import com.ericafenyo.carehub.entities.VitalReportEntity;
import com.ericafenyo.carehub.mapper.VitalReportMapper;
import com.ericafenyo.carehub.model.VitalReport;
import com.ericafenyo.carehub.repository.VitalReportRepository;
import com.ericafenyo.carehub.services.VitalReportService;
import com.ericafenyo.carehub.services.VitalService;
import com.ericafenyo.carehub.services.validation.Validations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VitalReportServiceImpl implements VitalReportService {
    private final VitalService vitalService;
    private final VitalReportRepository repository;
    private final VitalReportMapper mapper;
    private final Validations validations;

    @Override
    public List<VitalReport> getVitalReports(UUID teamId) {
        validations.validateTeamExistsById(teamId);

        return repository.findByTeamId(teamId)
                .stream()
                .map(mapper)
                .toList();
    }

    @Override
    public VitalReport createVitalReport(
            TeamEntity team,
            UserEntity user,
            CreateVitalReportRequest request
    ) {

        var vitalReport = new VitalReportEntity();
        vitalReport.setTeam(team);
        vitalReport.setMember(user);
        vitalReport.setRecordedAt(LocalDateTime.now(ZoneOffset.UTC));
        vitalReport.setNotes(request.getNotes());


        var vitals = request.getMeasurements().stream().map(item -> {
            var vital = vitalService.findVitalById(item.getVitalId());
            var measurement = new VitalMeasurementEntity();
            measurement.setVital(vital);
            measurement.setValue(item.getValue());
            measurement.setReport(vitalReport);
            return measurement;
        }).toList();

        vitalReport.setVitals(vitals);

        return repository.save(vitalReport).map(mapper);
    }

    @Override
    public VitalReport getVitalReport(UUID reportId) {
        return repository.findById(reportId)
                .map(mapper)
                .orElseThrow();
    }
}
