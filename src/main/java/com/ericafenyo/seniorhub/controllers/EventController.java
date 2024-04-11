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

import com.ericafenyo.seniorhub.dto.CreateEventRequest;
import com.ericafenyo.seniorhub.dto.UpdateEventRequest;
import com.ericafenyo.seniorhub.model.Event;
import com.ericafenyo.seniorhub.services.EventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

  private final EventService service;

  public EventController(EventService service) {
    this.service = service;
  }

  @GetMapping("events")
  public List<Event> getEvents() throws Exception {
    return service.getEvents();
  }

  @GetMapping("events/{id}")
  public Event getUserById(@PathVariable String id) throws Exception {
    return service.getEventById(id);
  }

  @PostMapping("events")
  public Event createUser(@RequestBody @Valid CreateEventRequest request) throws Exception {
    return service.createEvent(request);
  }

  @PutMapping("events/{id}")
  public Event updateUser(@PathVariable String id, @RequestBody @Valid UpdateEventRequest userUpdateDto) {
    return service.updateEvent(id, userUpdateDto);
  }

  @DeleteMapping("events/{id}")
  public void deleteEvent(@PathVariable String id) {
    service.deleteEvent(id);
  }
}
