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

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Builder;

import java.time.Duration;
import java.util.Optional;

/**
 * An interface representing a simple caching mechanism.
 */
public class Cache {
  private final com.github.benmanes.caffeine.cache.Cache<String, Object> caffeineCache;

  @Builder
  public Cache(long size, Duration duration) {
    caffeineCache = Caffeine.newBuilder()
        .maximumSize(size)
        .expireAfterWrite(duration)
        .build();
  }


  /**
   * Retrieves the value associated with the specified key from the cache.
   *
   * @param key  The key to look up in the cache.
   * @param type The expected type of the cached value.
   * @param <T>  The generic type of the expected value.
   * @return An {@link Optional} containing the cached value or an empty {@link Optional} if not found.
   */
  @SuppressWarnings("unchecked")
  public <T> Optional<T> get(String key, Class<T> type) {
    Object value = caffeineCache.getIfPresent(key);

    return Optional.ofNullable((T) value);
  }

  /**
   * Stores the given value in the cache with the specified key.
   *
   * @param key   The key under which the value will be stored in the cache.
   * @param value The value to be cached.
   */
  public void put(String key, Object value) {
    caffeineCache.put(key, value);
  }

  /**
   * Removes the entry with the specified key from the cache.
   *
   * @param key The key of the entry to be removed from the cache.
   */
  public void remove(String key) {
    caffeineCache.invalidate(key);
  }

  /**
   * Clears all entries from the cache.
   */
  public void invalidate() {
    caffeineCache.invalidateAll();
  }
}
