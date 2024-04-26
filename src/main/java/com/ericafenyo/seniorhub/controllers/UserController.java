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

package com.ericafenyo.seniorhub.controllers;

import com.ericafenyo.seniorhub.dto.UserCreationDto;
import com.ericafenyo.seniorhub.dto.UserUpdateDto;
import com.ericafenyo.seniorhub.model.Role;
import com.ericafenyo.seniorhub.model.User;
import com.ericafenyo.seniorhub.services.UserService;
import com.ericafenyo.seniorhub.validation.constraints.UserRole;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping()
  public List<User> getUsers() {
    return service.getUsers();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable String id) throws Exception {
    return service.getUserById(id);
  }

  @PostMapping()
  public Object createUser(
      @RequestBody UserCreationDto userCreationDto,
      @RequestParam("role") @UserRole String slug
  ) throws Exception {
    return service.createUser(userCreationDto, Role.from(slug));
  }

  @PutMapping("/{id}")
  public User updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateDto userUpdateDto) {
    return service.updateUser(id, userUpdateDto);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable String id) {
    service.deleteUser(id);
  }
}
