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

package com.ericafenyo.carehub.fake;

import com.ericafenyo.carehub.model.PartialUser;
import com.ericafenyo.carehub.model.VitalMeasurement;
import com.ericafenyo.carehub.model.VitalReport;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class Generator {
    public static VitalReport generateFakeVitalReport() {
        VitalReport report = new VitalReport();
        report.setId(UUID.randomUUID());
        report.setNotes("Sample notes");
        report.setRecordedAt(LocalDateTime.now());
        report.setMember(generateFakePartialUser());
        report.setMeasurements(Arrays.asList(generateFakeVitalMeasurement(), generateFakeVitalMeasurement()));
        return report;
    }

    private static PartialUser generateFakePartialUser() {
        PartialUser user = new PartialUser();
        user.setId(UUID.randomUUID());
        user.setFirstName("John");
        user.setLastName("Doe");
        return user;
    }

    private static VitalMeasurement generateFakeVitalMeasurement() {
        VitalMeasurement measurement = new VitalMeasurement();
        measurement.setId(UUID.randomUUID());
        measurement.setType("Blood Pressure");
        measurement.setValue("120/80");
        measurement.setRecordedAt(Instant.now());
        return measurement;
    }
}
