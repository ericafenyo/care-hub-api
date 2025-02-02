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

package com.ericafenyo.carehub.validation;

import com.ericafenyo.carehub.validation.constraints.UserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Getter;

import java.util.Arrays;

public class RoleValidator implements ConstraintValidator<UserRole, String> {

    /**
     * Enum representing different roles in the application.
     */
    @Getter
    private enum Role {
        COORDINATOR("coordinator"),
        AID("aid"),
        CONSULTANT("consultant"),
        ADMINISTRATOR("administrator");

        final String slug;

        /**
         * Constructor for Role enum.
         *
         * @param slug The URL-friendly slug associated with the role.
         */
        Role(String slug) {
            this.slug = slug;
        }

        /**
         * Retrieves the Role enum based on the given slug.
         *
         * @param slug The slug to match.
         * @return The corresponding Role enum.
         * @throws IllegalArgumentException If no role is found for the given slug.
         */
        @SuppressWarnings("UnusedReturnValue")
        public static Boolean match(String slug) {
            return Arrays.stream(values())
                .anyMatch(role -> role.slug.equalsIgnoreCase(slug));
        }
    }


    @Override
    public void initialize(UserRole constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Role.match(value);
    }
}
