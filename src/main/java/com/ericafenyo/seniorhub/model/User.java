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

package com.ericafenyo.seniorhub.model;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A user domain model representing an individual who interacts with the application.
 */
@Data
public class User {
  /**
   * The unique identifier for the user.
   */
  private String id;
  /**
   * The first name of the user.
   */
  private String firstName;
  /**
   * The last name of the user.
   */
  private String lastName;
  /**
   * The email address of the user.
   */
  private String email;
  /**
   * The URL pointing to the user's profile photo.
   */
  private String photoUrl;
  /**
   * The date and time when the user was created.
   */
  private LocalDateTime createdAt;
  /**
   * The date and time when the user was last updated.
   */
  private LocalDateTime updatedAt;

  /**
   * The role assigned to the user
   */
  private Role role;

  /**
   * The address where the user leaves.
   */
  private Address address;
}
