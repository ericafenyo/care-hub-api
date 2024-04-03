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

@Entity(name = "permissions")
@EntityListeners(AuditingEntityListener.class)
@Data
public class PermissionEntity {

  /**
   * The unique identifier for the permission.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * The name of the permission
   */
  @Column(name = "name")
  private String name;

  /**
   * A brief description or explanation of the permission.
   */
  @Column(name = "description")
  private String description;

  /**
   * The date and time when the permission was created.
   */
  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /**!
   * The date and time when the permission was last updated.
   */
  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToMany(mappedBy = "permissions")
  private List<RoleEntity> roles = new ArrayList<>();
}
