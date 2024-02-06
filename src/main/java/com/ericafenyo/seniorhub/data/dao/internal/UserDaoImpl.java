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

package com.ericafenyo.seniorhub.data.dao.internal;

import com.ericafenyo.seniorhub.controller.request.CreateUserRequest;
import com.ericafenyo.seniorhub.data.dao.UserDao;
import com.ericafenyo.seniorhub.data.entity.AddressEntity;
import com.ericafenyo.seniorhub.data.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserDaoImpl implements UserDao {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public List<UserEntity> findAll() {
    List<UserEntity> entities = new ArrayList<>();

    return entities;
  }

  @Override
  @Transactional
  public UserEntity insert(CreateUserRequest request) {
    var user = new UserEntity();
    user.setUuid(UUID.randomUUID().toString());
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setEmail("john.doe@example.com");
    user.setPhotoUrl("https://example.com/photos/1");

    var address = new AddressEntity();
    address.setCountry("France");
    address.setStreet("19 rue de la Procé");
    address.setPostalCode("75100");
    address.setCity("Paris");
    address.setCountry("France");

    manager.persist(address);

    user.setAddress(address);

    manager.persist(user);
    return user;
  }
}
