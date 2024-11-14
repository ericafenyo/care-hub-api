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

package com.ericafenyo.seniorhub.entities;

import com.ericafenyo.seniorhub.model.Frequency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity(name = "recurrences")
@EntityListeners(AuditingEntityListener.class)
@Data @Accessors(chain = true)
public class RecurrenceEntity {
    /**
     * The unique identifier for the recurrence.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * How often the task should be repeated.
     */
    @Column(name = "frequency")
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    /**
     * The number of times the task should be repeated.
     */
    @Column(name = "count")
    private int count = 0;

    /**
     * The number of times the task has been repeated.
     */
    @Column(name = "occurrences")
    private int occurrences = 1;

    /**
     * The day of the week the task should be repeated.
     */
    @Column(name = "day_of_week")
    private int dayOfWeek = 1;

    /**
     * The week of the month the task should be repeated.
     */
    @Column(name = "week_of_month")
    private int weekOfMonth = 1;

    /**
     * The day of the month the task should be repeated.
     */
    @Column(name = "day_of_month")
    private int dayOfMonth = 1;

    /**
     * The month of the year the task should be repeated.
     */
    @Column(name = "month_of_year")
    private int monthOfYear = 1;
}
