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

package com.ericafenyo.carehub.domain.mapper;

import com.ericafenyo.carehub.domain.model.Permission;
import com.ericafenyo.carehub.entities.PermissionEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.function.Function;

/**
 * A class responsible for converting a {@link PermissionEntity} to a {@link Permission}.
 */
@Component
public class PermissionMapper implements Function<PermissionEntity, Permission> {

    /**
     * Converts a {@link PermissionEntity} to a {@link }.
     *
     * @param entity the {@link PermissionEntity} to be converted
     * @return the corresponding {@link } object
     */
    @Override
    public Permission apply(PermissionEntity entity) {
        Assert.notNull(entity, "Permission entity cannot be null");
        var permission = new Permission();
        permission.setId(entity.getId());
        permission.setName(entity.getName());
        permission.setDescription(entity.getDescription());
        return permission;
    }
}
