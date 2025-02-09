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

package com.ericafenyo.carehub.repository;

import com.ericafenyo.carehub.entities.VitalMeasurementEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for performing database operations on a {@link VitalMeasurementEntity}.
 */
@Repository
public interface VitalMeasurementRepository extends AbstractRepository<VitalMeasurementEntity> {

    // Custom query to get the latest vital measurement for each vital type for a given team
    @Query("SELECT vm FROM vital_measurements vm " +
            "WHERE vm.report.team.id = :teamId " +
            "AND vm.createdAt = (SELECT MAX(v2.createdAt) FROM vital_measurements v2 " +
            "WHERE v2.vital.id = vm.vital.id AND v2.report.team.id = :teamId) ")
    List<VitalMeasurementEntity> findLatestVitalMeasurementsForTeam(@Param("teamId") UUID teamId);
}
