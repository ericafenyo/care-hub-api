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

package com.ericafenyo.carehub.exceptions.handlers;

import com.ericafenyo.carehub.exceptions.HttpExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.security.util.FieldUtils.getFieldValue;


/**
 * Global exception handler for handling validation errors in request payloads.
 */
@ControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConstraintViolationExceptionHandler {

    private static final String ERROR_CODE = "validation_error";

    /**
     * Handles ConstraintViolationException and returns a ResponseEntity with a standardized error response.
     *
     * @param exception The exception to be handled.
     * @return ResponseEntity with a standardized error response.
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handle(ConstraintViolationException exception, HttpServletRequest request) {
        // Prepare response object
        HttpExceptionResponse response = new HttpExceptionResponse();
        List<Object> objects = new ArrayList<>();

        // Accumulate constraints for each field
        Map<String, Map<String, Object>> accumulator = new LinkedHashMap<>();

        // Get all constraint violations from the exception
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        violations.forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();

            // If the field is encountered for the first time, initialize a new map for its constraints
            accumulator.computeIfAbsent(field, key -> new LinkedHashMap<>());

            // Add the current constraint to the map for the field
            accumulator.get(field).put(field, errorMessage);
        });



        // Create the final entry with all constraints for each field
        accumulator.forEach((field, constraints) -> {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("property", field);
            entry.put("value", constraints.get(field)); // Use the value of the field
            entry.put("constraints", constraints);
            objects.add(entry);
        });

        response.setPath(request.getRequestURI());
        response.setMessage(objects);
        response.setCode(ERROR_CODE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
