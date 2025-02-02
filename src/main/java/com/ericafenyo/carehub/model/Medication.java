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

package com.ericafenyo.carehub.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Accessors(chain = true)
public class Medication {
  /**
   * The unique identifier for the medication.
   */
  private UUID id;
  /**
   * The name of the medication administered or taken by the user.
   */
  private String name;
  /**
   * The dosage of the medication.
   */
  private String dosage;
  /**
   * The method by which the medication is to be taken.
   */
  private String route;
  /**
   * The frequency at which the medication is to be taken.
   */
  private String frequency;

  /**
   * The instructions for taking the medication.
   */
  private String instructions;

  /**
   * The date the medication is to be taken.
   */
  private LocalDate startDate;
  /**
   * The date the medication is to be stopped.
   */
  private LocalDate endDate;

  /**
   * The user associated with the medication record.
   */
  private User user;

  /**
   * The team associated with the medication record.
   */
  private Team team;

  /**
   * The timestamp when the medication record was created.
   */
  private Instant createdAt;

  /**
   * The timestamp when the medication record was last updated.
   */
  private Instant updatedAt;
}
