/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2025 Eric Afenyo
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

package com.ericafenyo.seniorhub.services.impl;

import com.ericafenyo.seniorhub.Messages;
import com.ericafenyo.seniorhub.dto.CreateUserRequest;
import com.ericafenyo.seniorhub.dto.UserUpdateDto;
import com.ericafenyo.seniorhub.entities.AddressEntity;
import com.ericafenyo.seniorhub.entities.CityEntity;
import com.ericafenyo.seniorhub.entities.CountryEntity;
import com.ericafenyo.seniorhub.entities.CredentialEntity;
import com.ericafenyo.seniorhub.entities.UserEntity;
import com.ericafenyo.seniorhub.exceptions.ConflictException;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.exceptions.NotFoundException;
import com.ericafenyo.seniorhub.mapper.UserMapper;
import com.ericafenyo.seniorhub.model.Membership;
import com.ericafenyo.seniorhub.model.Team;
import com.ericafenyo.seniorhub.model.User;
import com.ericafenyo.seniorhub.repository.CityRepository;
import com.ericafenyo.seniorhub.repository.CountryRepository;
import com.ericafenyo.seniorhub.repository.CredentialRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.services.TeamService;
import com.ericafenyo.seniorhub.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper toUser;
    private final Messages messages;

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    private final TeamService teamService;

    @Override
    @Transactional
    public User createUser(CreateUserRequest request) throws HttpException {
        // Validate if a user with the provided email already exists
        validateUserExistByEmail(request.getEmail());

        // Create a new city entity
        var city = cityRepository.findByName(request.getAddress().getCity())
                .orElseGet(() -> cityRepository.save(new CityEntity().setName(request.getAddress().getCity())));

        // Create a new country entity
        var country = countryRepository.findByName(request.getAddress().getCountry())
                .orElseGet(() -> countryRepository.save(new CountryEntity().setName(request.getAddress().getCountry())));

        // Create a new address entity
        var address = new AddressEntity();
        address.setStreet(request.getAddress().getStreet());
        address.setPostalCode(request.getAddress().getPostalCode());
        address.setCity(city);
        address.setCountry(country);

        // Create and save a new user entity
        var user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        user.setEmail(request.getEmail());
        user.setAddress(address);
        var savedUser = userRepository.save(user);

        // Create and save new credential entity
        var credential = new CredentialEntity();
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        credential.setPassword(hashedPassword);
        credential.setUser(savedUser);
        credentialRepository.save(credential);

        return toUser.apply(savedUser);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        var entities = userRepository.findAll();
        for (UserEntity entity : entities) {
            users.add(toUser.apply(entity));
        }

        return users;
    }

    @Override
    public User getUserById(UUID userId) throws NotFoundException {
        return findUserById(userId).map(toUser);
    }

    @Override
    public User updateUser(UUID userId, UserUpdateDto dto) throws HttpException {
        var user = findUserById(userId);

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

        return toUser.apply(savedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.findById(id).ifPresent(team -> userRepository.delete(team));
    }

    @Override
    public List<Team> getUserTeams(UUID userId) throws HttpException {
        // Find the current user using the provided id
        var user = findUserById(userId);

        return teamService.getUserTeams(user.getId());
    }

    @Override
    public List<Membership> getMemberships(UUID userId) throws HttpException {
        var user = this.findUserById(userId);
        return teamService.getMemberships(user.getId());
    }

    /**
     * Find a user by the provided id.
     *
     * @param userId The id of the user to find.
     * @return The user entity if found.
     * @throws NotFoundException If the user is not found, a NotFoundException is thrown.
     */
    private UserEntity findUserById(UUID userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
                messages.format("error.resource.not.found", "User"),
                messages.format("error.resource.not.found.code", "user")
        ));
    }

    /**
     * Validate if a user with the provided email already exists.
     *
     * @param email The email to validate.
     * @throws ConflictException If the email already exists, a ConflictException is thrown.
     */
    private void validateUserExistByEmail(String email) throws ConflictException {
        boolean isPresent = userRepository.existsByEmail(email);
        if (isPresent) {
            throw new ConflictException(
                    messages.format("error.resource.already.exists", "User"),
                    messages.format("error.resource.already.exists.code", "user")
            );
        }
    }
}
