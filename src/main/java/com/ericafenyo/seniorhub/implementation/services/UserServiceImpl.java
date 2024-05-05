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

package com.ericafenyo.seniorhub.implementation.services;

import com.ericafenyo.seniorhub.dto.UserCreationDto;
import com.ericafenyo.seniorhub.dto.UserUpdateDto;
import com.ericafenyo.seniorhub.entities.AddressEntity;
import com.ericafenyo.seniorhub.entities.CityEntity;
import com.ericafenyo.seniorhub.entities.CountryEntity;
import com.ericafenyo.seniorhub.entities.CredentialEntity;
import com.ericafenyo.seniorhub.entities.UserEntity;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.exceptions.InternalServerErrorException;
import com.ericafenyo.seniorhub.exceptions.user.UserExistsException;
import com.ericafenyo.seniorhub.exceptions.user.UserNotFoundException;
import com.ericafenyo.seniorhub.mapper.UserMapper;
import com.ericafenyo.seniorhub.model.User;
import com.ericafenyo.seniorhub.repository.CredentialRepository;
import com.ericafenyo.seniorhub.repository.RoleRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Override
    @Transactional
    public User createUser(UserCreationDto dto, String roleSlug) throws HttpException {
        // Throw error if the user already exists
        boolean isPresent = userRepository.existsByEmail(dto.getEmail());
        if (isPresent) {
            throw new UserExistsException();
        }

        // Create a new city entity
        var city = new CityEntity().setName(dto.getAddress().getCity());

        // Create a new country entity
        var country = new CountryEntity().setName(dto.getAddress().getCountry());

        // Create a new address entity
        var address = new AddressEntity();
        address.setStreet(dto.getAddress().getStreet());
        address.setPostalCode(dto.getAddress().getPostalCode());
        address.setCity(city);
        address.setCountry(country);

        // Find the role entity by slug
        var role = roleRepository.findBySlug(roleSlug)
                .orElseThrow(() -> new InternalServerErrorException("Role not found"));

        // Create and save a new user entity
        var user = new UserEntity();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setBirthDate(dto.getBirthdate());
        user.setEmail(dto.getEmail());
        user.setAddress(address);
        user.setRole(role);
        var savedUser = userRepository.save(user);

        // Create and save new credential entity
        var credential = new CredentialEntity();
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        credential.setPassword(hashedPassword);
        credential.setUser(savedUser);
        credentialRepository.save(credential);

        return mapper.apply(savedUser);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        var entities = userRepository.findAll();
        for (UserEntity entity : entities) {
            users.add(mapper.apply(entity));
        }

        return users;
    }

    @Override
    public User getUserById(String id) throws UserNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return mapper.apply(user.get());
    }


    @Override
    public User updateUser(String id, UserUpdateDto dto) {
        UserEntity user = userRepository.findById(id).get();

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

        UserEntity savedUser = userRepository.save(user);

        return mapper.apply(savedUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
