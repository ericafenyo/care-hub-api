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

package com.ericafenyo.seniorhub.internal.repositories;

import com.ericafenyo.seniorhub.dao.UserDao;
import com.ericafenyo.seniorhub.entity.UserEntity;
import com.ericafenyo.seniorhub.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
  @PersistenceContext
  private EntityManager manager;

  private final UserDao userDao;

  UserRepositoryImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  @Transactional
  public UserEntity save(UserEntity entity) {
    return userDao.save(entity);
  }

  @Override
  @Transactional
  public Iterable<UserEntity> findAll() {
    return userDao.findAll();
  }

  @Override
  @Transactional
  public UserEntity findById(String id) {
    TypedQuery<UserEntity> query = manager.createQuery("SELECT e FROM users as e WHERE uuid=:uuid", UserEntity.class)
        .setParameter("uuid", id);

    return query.getSingleResult();
  }

  @Override
  @Transactional
  public void delete(String id) {
    int updated = manager.createQuery("DELETE FROM users as e WHERE e.uuid=:uuid")
        .setParameter("uuid", id)
        .executeUpdate();

    System.out.println("Deleted count:" + updated);
  }
}
