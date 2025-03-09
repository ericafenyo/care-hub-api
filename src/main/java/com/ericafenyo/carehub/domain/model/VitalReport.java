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

package com.ericafenyo.carehub.domain.model;

import com.ericafenyo.carehub.model.PartialUser;
import com.ericafenyo.carehub.model.VitalMeasurement;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * A vital report is a collection of vital measurements taken at a specific time.
 */
@Getter @Setter @Accessors(chain = true)
public class VitalReport {
    /**
     * The unique identifier of the vital report.
     */
    private UUID id;

    /**
     * A short note written by the person who recorded the vital report.
     */
    private String notes;

    /**
     * The date and time the vital report was recorded.
     */
    private LocalDateTime recordedAt;

    /**
     * The member whose vital measurements are recorded in the report.
     */
    private PartialUser member;

    /**
     * The list of vital measurements recorded in the report.
     */
    private List<VitalMeasurement> measurements;
}
