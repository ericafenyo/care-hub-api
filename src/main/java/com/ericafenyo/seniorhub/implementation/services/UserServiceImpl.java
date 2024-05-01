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

import com.ericafenyo.seniorhub.EnvironmentVariables;
import com.ericafenyo.seniorhub.dto.InvitationRequest;
import com.ericafenyo.seniorhub.dto.UserCreationDto;
import com.ericafenyo.seniorhub.dto.UserUpdateDto;
import com.ericafenyo.seniorhub.entities.AddressEntity;
import com.ericafenyo.seniorhub.entities.CityEntity;
import com.ericafenyo.seniorhub.entities.CountryEntity;
import com.ericafenyo.seniorhub.entities.CredentialEntity;
import com.ericafenyo.seniorhub.entities.InvitationEntity;
import com.ericafenyo.seniorhub.entities.UserEntity;
import com.ericafenyo.seniorhub.exceptions.HttpException;
import com.ericafenyo.seniorhub.exceptions.user.UserExistsException;
import com.ericafenyo.seniorhub.exceptions.user.UserNotFoundException;
import com.ericafenyo.seniorhub.mapper.UserMapper;
import com.ericafenyo.seniorhub.model.Mail;
import com.ericafenyo.seniorhub.model.Report;
import com.ericafenyo.seniorhub.model.Role;
import com.ericafenyo.seniorhub.model.User;
import com.ericafenyo.seniorhub.repository.CredentialRepository;
import com.ericafenyo.seniorhub.repository.InvitationRepository;
import com.ericafenyo.seniorhub.repository.UserRepository;
import com.ericafenyo.seniorhub.services.MailService;
import com.ericafenyo.seniorhub.services.UserService;
import com.ericafenyo.seniorhub.util.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final MailService mailService;
    private final EnvironmentVariables environment;
    private final InvitationRepository invitationRepository;
    private final UserMapper mapper;

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
    public User createUser(UserCreationDto dto, Role role) throws HttpException {
        // Throw error if the user already exists
        boolean isPresent = userRepository.existsByEmail(dto.getEmail());
        if (isPresent) {
            throw new UserExistsException();
        }

        var user = new UserEntity();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUuid(UUID.randomUUID().toString());

        var city = new CityEntity();
        city.setName(dto.getAddress().getCity());

        var country = new CountryEntity();
        country.setName(dto.getAddress().getCountry());

        var address = new AddressEntity();
        address.setStreet(dto.getAddress().getStreet());
        address.setUuid(UUID.randomUUID().toString());
        address.setPostalCode(dto.getAddress().getPostalCode());
        address.setCity(city);
        address.setCountry(country);

        user.setAddress(address);

        var savedUser = userRepository.save(user);

        var credential = new CredentialEntity();
        String hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        credential.setPassword(hashedPassword);
        credential.setUser(savedUser);
        credentialRepository.save(credential);

        return mapper.apply(userRepository.save(user));
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

    @Override
    public Report inviteUser(String id, InvitationRequest request) {
        // Get the inviter
        var inviter = userRepository.findById(request.getInviter().getId());
        var senior = userRepository.findById(id);

        if (inviter.isEmpty() || senior.isEmpty()) {
            // Throw forbidden
            throw new RuntimeException(new HttpException(HttpStatus.FORBIDDEN, "", ""));
        }

        var recipientEmail = request.getInvitee().getEmail();
        String token = Hashing.randomSHA256();
//        var invitation = new InvitationEntity()
//                .setToken(token)
//                .setEmail(recipientEmail)
//                .setRole(Role.CARETAKER)
//                .setSenior(senior.get());


//        var saved = invitationRepository.save(invitation);
//        var fetched = invitationRepository.findById("3fd4c50e-8f54-4c20-974a-c2bfd6b607d0");
        var fetched = invitationRepository.existsById("3fd4c50e-8f54-4c20-974a-c2bfd6b607d0");
        System.out.println(fetched);

//        System.out.println("saved: " + saved);

//        var baseUrl = environment.getBaseUrl();

//        var link = "%s?token=%s".formatted(baseUrl, token);
//        var context = new Mail.Context();
//        context.put("name", "John");
//        context.put("link", link);
//
        return null;

//        return mailService.sendInvitation(request.getInvitee().getEmail(), context);
    }
}
