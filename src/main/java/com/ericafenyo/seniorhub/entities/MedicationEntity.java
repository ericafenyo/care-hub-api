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

package com.ericafenyo.seniorhub.entities;

import com.ericafenyo.seniorhub.api.Mappable;
import com.ericafenyo.seniorhub.model.Medication;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

@Entity(name = "medications")
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@Accessors(chain = true)
public class MedicationEntity implements Mappable<MedicationEntity, Medication> {
    /**
     * The unique identifier for the medication.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * The name of the medication administered or taken by the user.
     */
    @Column(name = "name")
    private String name;

    /**
     * The dosage of the medication.
     */
    @Column(name = "dosage")
    private String dosage;

    /**
     * The frequency at which the medication is to be taken.
     */
    @Column(name = "frequency")
    private String frequency;

    /**
     * The date and time the medication is to be taken.
     */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /**
     * The date and time the medication is to be stopped.
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * The team associated with the medication record.
     */
    @ManyToOne()
    @JoinColumn(name = "team_id")
    private TeamEntity team;


    /**
     * The author of the note.
     */
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * The timestamp when the medication record was created.
     */
    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    /**
     * The timestamp when the medication record was last updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Override
    public Medication map(Function<? super MedicationEntity, ? extends Medication> mapper) {
        return mapper.apply(this);
    }
}
