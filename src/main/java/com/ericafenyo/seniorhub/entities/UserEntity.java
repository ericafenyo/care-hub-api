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

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A database entity representing an individual or entity who interacts with the application.
 */
@Entity(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data
public class UserEntity {

  /**
   * The unique identifier for the user.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * A secondary unique identifier.
   */
  @Column(name = "uuid", unique = true)
  private String uuid;

  /**
   * The first name of the user.
   */
  @Column(name = "first_name", length = 50)
  private String firstName;

  /**
   * The last name of the user.
   */
  @Column(name = "last_name", length = 50)
  private String lastName;

  /**
   * The email address of the user.
   */
  @Column(name = "email", unique = true)
  private String email;

  /**
   * The URL pointing to the user's profile photo.
   */
  @Column(name = "photo")
  private String photoUrl;

  /**
   * The date and time when the user was created.
   */
  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /**
   * The date and time when the user was last updated.
   */
  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  /**
   * Represents an address where the user leaves.
   */
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "address_id")
  private AddressEntity address;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private RoleEntity role;
}
