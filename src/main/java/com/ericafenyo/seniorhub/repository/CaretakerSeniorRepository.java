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

package com.ericafenyo.seniorhub.repository;

import com.ericafenyo.seniorhub.entities.CaretakerSeniorEntity;
import org.springframework.data.repository.Repository;

/**
 * This is a Spring Data repository for the CaretakerSeniorEntity.
 * It provides the mechanism for storage, retrieval, and search,
 * database operations for the CaretakerSeniorEntity.
 * <p>
 * Its parent class is a Spring Data Repository interface which is a central repository marker interface.
 *
 * @author Eric Afenyo
 * @version 1.0
 * @since 2024-01-01
 */
@org.springframework.stereotype.Repository
public interface CaretakerSeniorRepository extends Repository<CaretakerSeniorEntity, Void> {
    /**
     * This method is used to save a CaretakerSeniorEntity into the database.
     * It takes a CaretakerSeniorEntity as a parameter and returns the saved entity.
     *
     * @param entity This is a parameter of type CaretakerSeniorEntity to be saved in the database.
     * @return CaretakerSeniorEntity This returns the saved CaretakerSeniorEntity.
     */
    CaretakerSeniorEntity save(CaretakerSeniorEntity entity);
}