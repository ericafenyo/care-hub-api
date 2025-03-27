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

package com.ericafenyo.carehub.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class representing HTTP-related errors.
 */
@Getter
@Builder
public class DomainException extends RuntimeException {

    /**
     * The HTTP status associated with the exception.
     */
    private final HttpStatus status;

    /**
     * A unique error code associated with the exception.
     */
    private final String code;

    /**
     * A unique error code associated with the exception.
     */
    private String message;

    /**
     * Constructs a new {@code HttpException} with the specified HTTP status, error message, and code.
     *
     * @param status  The HTTP status code representing the error.
     * @param message A descriptive error message.
     * @param code    A unique error code associated with the exception.
     */
    public DomainException(HttpStatus status, String message, String code) {
        super(message);
        this.message = message;
        this.status = status;
        this.code = code;
    }

    /**
     * Constructs a new {@code HttpException} with the specified HTTP status, error message, code, and a cause.
     *
     * @param status  The HTTP status code representing the error.
     * @param message A descriptive error message.
     * @param code    A unique error code associated with the exception.
     * @param cause   The cause of the exception.
     */
    public DomainException(HttpStatus status, String message, String code, Throwable cause) {
        super(message, cause);
//        this.message = message;
        this.status = status;
        this.code = code;
    }
}

