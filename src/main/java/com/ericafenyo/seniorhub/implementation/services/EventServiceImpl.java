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

import com.ericafenyo.seniorhub.dto.CreateEventRequest;
import com.ericafenyo.seniorhub.dto.UpdateEventRequest;
import com.ericafenyo.seniorhub.entities.EventEntity;
import com.ericafenyo.seniorhub.exceptions.event.EventNotFoundException;
import com.ericafenyo.seniorhub.mapper.EventMapper;
import com.ericafenyo.seniorhub.model.Event;
import com.ericafenyo.seniorhub.repository.EventRepository;
import com.ericafenyo.seniorhub.services.EventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

  private final EventRepository repository;
  private final EventMapper mapper;

  public EventServiceImpl(EventRepository repository, EventMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<Event> getEvents() {
    List<Event> events = new ArrayList<>();

    var entities = repository.findAll();
    for (EventEntity entity : entities) {
      events.add(mapper.apply(entity));
    }

    return events;
  }

  @Override
  public Event getEventById(String id) throws EventNotFoundException {
    Optional<EventEntity> event = repository.findById(id);

    if (event.isEmpty()) {
      throw new EventNotFoundException();
    }

    return mapper.apply(event.get());
  }

  @Override
  public Event createEvent(CreateEventRequest request) {
    EventEntity entity = new EventEntity();
    entity.setUuid(UUID.randomUUID().toString());
    entity.setName(request.getName());
    entity.setDescription(request.getDescription());
    entity.setStartDate(request.getStartDate());
    entity.setStartTime(request.getStartTime());
    entity.setEndDate(request.getEndDate());
    entity.setEndTime(request.getEndTime());

    return mapper.apply(repository.save(entity));
  }

  @Override
  public Event updateEvent(String id, UpdateEventRequest userUpdateDto) {
    return null;
  }

  @Override
  public void deleteEvent(String id) {

  }
}
