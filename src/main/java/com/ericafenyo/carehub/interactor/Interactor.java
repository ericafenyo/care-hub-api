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

package com.ericafenyo.carehub.interactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Interactor<T, R> {
    private static final Logger logger = LoggerFactory.getLogger(Interactor.class);

    /**
     * Executes the business logic.
     *
     * @param params Parameters required for the business logic.
     * @return The result of the business logic execution.
     * @throws RuntimeException If an error occurs during execution.
     */
    public R execute(T params) {
        try {
            return create(params);
        } catch (Exception e) {
            logger.error("Error executing interactor", e);
            throw new RuntimeException("Interactor execution failed", e);
        }
    }

    /**
     * Core business logic to be implemented by subclasses.
     *
     * @param params Parameters required for execution.
     * @return The result of the execution.
     * @throws Exception If an error occurs during execution.
     */
    protected abstract R create(T params) throws Exception;
}
