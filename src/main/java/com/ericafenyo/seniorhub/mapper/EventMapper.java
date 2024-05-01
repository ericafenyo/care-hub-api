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

package com.ericafenyo.seniorhub.mapper;

import com.ericafenyo.seniorhub.entities.EventEntity;
import com.ericafenyo.seniorhub.model.Event;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EventMapper implements Function<EventEntity, Event> {
  @Override
  public Event apply(EventEntity entity) {
    var event = new Event();
    event.setId(entity.getUuid());
    event.setName(entity.getName());
    event.setDescription(entity.getDescription());
    event.setStartDate(entity.getStartDate());
    event.setStartTime(entity.getStartTime());
    event.setEndDate(entity.getEndDate());
    event.setEndTime(entity.getEndTime());
    event.setCreatedAt(entity.getCreatedAt());
    event.setUpdatedAt(entity.getUpdatedAt());
    return event;
  }
}
