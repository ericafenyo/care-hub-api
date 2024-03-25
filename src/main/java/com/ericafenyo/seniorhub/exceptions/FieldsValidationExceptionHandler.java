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

package com.ericafenyo.seniorhub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Global exception handler for handling validation errors in request payloads.
 */
@ControllerAdvice
public class FieldsValidationExceptionHandler {

  private static final String ERROR_CODE = "validation_error";

  /**
   * Handles MethodArgumentNotValidException and returns a ResponseEntity with a standardized error response.
   *
   * @param exception The exception to be handled.
   * @return ResponseEntity with a standardized error response.
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handle(MethodArgumentNotValidException exception) {
    HttpExceptionResponse response = new HttpExceptionResponse();
    List<Object> objects = new ArrayList<>();

    // Accumulate constraints for each field
    Map<String, Map<String, Object>> accumulator = new LinkedHashMap<>();

    BindingResult result = exception.getBindingResult();
    List<ObjectError> errors = result.getAllErrors();
    errors.forEach(error -> {
      FieldError fieldError = (FieldError) error;
      String field = fieldError.getField();

      // If the field is encountered for the first time, initialize a new map for its constraints
      accumulator.computeIfAbsent(field, key -> new LinkedHashMap<>());

      // Add the current constraint to the map for the field
      accumulator.get(field).put(field, error.getDefaultMessage());
    });

    // Create the final entry with all constraints for each field
    accumulator.forEach((field, constraints) -> {
      Map<String, Object> entry = new LinkedHashMap<>();
      entry.put("property", field);
      entry.put("value", result.getFieldValue(field)); // You can use the original field value
      entry.put("constraints", constraints);
      objects.add(entry);
    });

    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setMessage(objects);
    response.setCode(ERROR_CODE);
    response.setTimestamp(LocalDateTime.now(ZoneOffset.UTC));

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
