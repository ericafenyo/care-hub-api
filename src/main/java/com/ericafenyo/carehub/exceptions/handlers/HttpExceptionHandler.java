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

import com.ericafenyo.carehub.exceptions.DomainException;
import com.ericafenyo.carehub.exceptions.HttpExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for HTTP exceptions. Annotated with {@code @ControllerAdvice} to allow centralized handling of exceptions across multiple controllers.
 */
@ControllerAdvice
public class HttpExceptionHandler {

    /**
     * Handles instances of {@code HttpException} and generates a standardized response.
     *
     * @param exception The HTTP exception to be handled.
     * @return A {@link ResponseEntity} containing an {@link HttpExceptionResponse} with relevant information.
     */
    @ExceptionHandler(value = {DomainException.class})
    ResponseEntity<Object> handle(DomainException exception, HttpServletRequest request) {
        HttpExceptionResponse response = new HttpExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setPath(request.getRequestURI());
        response.setCode(exception.getCode());
        return new ResponseEntity<>(response, exception.getStatus());
    }
}

