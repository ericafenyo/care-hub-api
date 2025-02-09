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

import com.ericafenyo.carehub.contexts.CreateTaskContext;
import com.ericafenyo.carehub.dto.CreateVitalReportRequest;
import com.ericafenyo.carehub.entities.VitalReportEntity;
import com.ericafenyo.carehub.exceptions.NotFoundException;
import com.ericafenyo.carehub.model.Membership;
import com.ericafenyo.carehub.dto.UpdateTeamRequest;
import com.ericafenyo.carehub.exceptions.HttpException;
import com.ericafenyo.carehub.model.Invitation;
import com.ericafenyo.carehub.model.Report;
import com.ericafenyo.carehub.model.Task;
import com.ericafenyo.carehub.model.Team;
import com.ericafenyo.carehub.model.VitalMeasurement;
import com.ericafenyo.carehub.model.VitalReport;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    Team createTeam(String name, String description) throws HttpException;

    List<Team> getTeams();

    Team getTeamById(UUID teamId) throws HttpException;

    Team updateTeam(UUID teamId, UpdateTeamRequest userUpdateDto);

    void deleteTeam(UUID teamId);

    List<Team> getUserTeams(UUID teamId) throws HttpException;

    Report addMember(UUID teamId, String role, String firstName, String lastName, String email) throws HttpException;

    Invitation validateInvitation(UUID teamId);

    Task createTask(CreateTaskContext context) throws HttpException;

    Membership getMembership(UUID teamId, UUID userId) throws HttpException;

    Membership getMembership(UUID teamId) throws HttpException;

    List<Membership> getMemberships(UUID userId) throws HttpException;

    List<VitalReport> getVitalReports(UUID teamId) throws HttpException;

    VitalReport createVitalReports(UUID teamId, CreateVitalReportRequest request) throws HttpException;

    VitalReport getVitalReport(UUID teamId, UUID reportId);

    List<VitalMeasurement> getVitalMeasurements(UUID teamId);
}
