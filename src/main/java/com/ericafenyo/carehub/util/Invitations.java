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

package com.ericafenyo.carehub.util;

import java.time.Duration;
import java.time.Instant;

/**
 * Utility class for handling date and time operations.
 */
public class Invitations {

    /**
     * Checks if the given expiration time is before a calculated minimum expiration time.
     *
     * @param expiration The expiration time to check.
     * @param minTtl     The minimum time-to-live duration.
     * @return true if the expiration time is before the minimum expiration time, false otherwise.
     */
    private static boolean willExpire(Instant expiration, Duration minTtl) {
        Instant now = Instant.now();
        // Calculate the minimum expiration time
        Instant minExpirationTime = now.plus(minTtl);
        return expiration.isBefore(minExpirationTime);
    }

    /**
     * Checks if the given time has passed, i.e., if it is before the current time.
     *
     * @param expiration The time to check for expiration.
     * @return true if the time has expired, false otherwise.
     */
    private static boolean isPassed(Instant expiration) {
        return expiration.isBefore(Instant.now());
    }

    /**
     * Checks if the given time has expired
     *
     * @param expiration The time to check for expiration.
     * @return true if the time has expired or will expire in a minute, false otherwise.
     */
    public static boolean hasExpired(Instant expiration) {
        return isPassed(expiration) || willExpire(expiration, Duration.ofMinutes(1));
    }
}
