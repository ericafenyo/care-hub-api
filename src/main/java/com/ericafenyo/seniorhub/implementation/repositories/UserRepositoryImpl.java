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

package com.ericafenyo.seniorhub.implementation.repositories;

import com.ericafenyo.seniorhub.dao.UserDao;
import com.ericafenyo.seniorhub.entities.UserEntity;
import com.ericafenyo.seniorhub.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
  @PersistenceContext
  private EntityManager manager;

  private final UserDao userDao;

  UserRepositoryImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserEntity save(UserEntity entity) {
    return userDao.save(entity);
  }

  @Override
  public Iterable<UserEntity> findAll() {
    return userDao.findAll();
  }

  @Override
  public Optional<UserEntity> findById(String id) {
    return userDao.findByUuid(id);
  }

  @Override
  public Optional<UserEntity> findByEmail(String email) {
    return userDao.findByEmail(email);
//    TypedQuery<UserEntity> query = manager.createQuery("SELECT e FROM users as e WHERE email=:email", UserEntity.class)
//        .setParameter("email", email);
//
//    Optional<UserEntity> first = query.getResultStream().findFirst();
//
//   var t = first.get();
//
//    return first;
  }

  @Override
  public boolean existsByEmail(String email) {
    return userDao.existsByEmail(email);
  }

  @Override
  public boolean existsById(String id) {
    return userDao.existsByUuid(id);
  }

  @Override
  public void delete(String id) {
    int updated = manager.createQuery("DELETE FROM users as e WHERE e.uuid=:uuid")
        .setParameter("uuid", id)
        .executeUpdate();
  }
}
