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

package com.ericafenyo.seniorhub.internal.services;

import com.ericafenyo.seniorhub.dto.UserCreationDto;
import com.ericafenyo.seniorhub.dto.UserUpdateDto;
import com.ericafenyo.seniorhub.entity.AddressEntity;
import com.ericafenyo.seniorhub.entity.CityEntity;
import com.ericafenyo.seniorhub.entity.CountryEntity;
import com.ericafenyo.seniorhub.entity.UserEntity;
import com.ericafenyo.seniorhub.mapper.UserMapper;
import com.ericafenyo.seniorhub.model.User;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository repository;
  @Autowired
  private UserMapper mapper;

  @Override
  public List<User> getUsers() {
    List<User> users = new ArrayList<>();

    var entities = repository.findAll();
    for (UserEntity entity : entities) {
      users.add(mapper.apply(entity));
    }

    return users;
  }

  @Override
  public User getUserById(String id) {
    UserEntity entity = repository.findById(id);
    return mapper.apply(entity);
  }

  @Override
  public User createUser(UserCreationDto dto) {
    var user = new UserEntity();
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail());
    user.setUuid(UUID.randomUUID().toString());

    CityEntity city = new CityEntity();
    city.setName(dto.getAddress().getCity());

    CountryEntity country = new CountryEntity();
    country.setName(dto.getAddress().getCountry());

    AddressEntity address = new AddressEntity();
    address.setStreet(dto.getAddress().getStreet());
    address.setPostalCode(dto.getAddress().getPostalCode());
    address.setCity(city);
    address.setCountry(country);

    user.setAddress(address);

    UserEntity saved = repository.save(user);
    return mapper.apply(saved);
  }

  @Override
  public User updateUser(String id, UserUpdateDto dto) {
    UserEntity user = repository.findById(id);

    AddressEntity address = user.getAddress();
    CityEntity city = address.getCity();
    city.setName(dto.getAddress().getCity());

    CountryEntity country = address.getCountry();
    country.setName(dto.getAddress().getCountry());

    address.setCity(city);
    address.setCountry(country);
    address.setStreet(dto.getAddress().getStreet());
    address.setPostalCode(dto.getAddress().getPostalCode());

    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setAddress(address);

    UserEntity savedUser = repository.save(user);

    return mapper.apply(savedUser);
  }

  @Override
  public void deleteUser(String id) {
    repository.delete(id);
  }
}
