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

import com.ericafenyo.seniorhub.entities.MedicationEntity;
import com.ericafenyo.seniorhub.model.Medication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MedicationMapper implements Function<MedicationEntity, Medication> {
  private final UserMapper userMapper;
  private final TeamMapper teamMapper;

  @Override
  public Medication apply(MedicationEntity entity) {
    Assert.notNull(entity, "Medication entity cannot be null");

    var medication = new Medication()
        .setId(entity.getId())
        .setName(entity.getName())
        .setDosage(entity.getDosage())
        .setRoute(entity.getRoute())
        .setInstructions(entity.getInstructions())
        .setFrequency(entity.getFrequency())
        .setStartDate(entity.getStartDate())
        .setEndDate(entity.getEndDate())
        .setCreatedAt(entity.getCreatedAt())
        .setUpdatedAt(entity.getUpdatedAt());

    Optional.ofNullable(entity.getUser())
        .ifPresent(author -> medication.setUser(userMapper.apply(author)));

    Optional.ofNullable(entity.getTeam())
        .ifPresent(team -> medication.setTeam(teamMapper.apply(team)));

    return medication;
  }
}
