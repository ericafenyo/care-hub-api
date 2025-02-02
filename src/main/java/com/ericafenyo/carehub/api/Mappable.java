package com.ericafenyo.carehub.api;

import java.util.function.Function;

/**
 * An interface for a generic mapper.
 *
 * @param <T> the type of the input to the mapper function
 * @param <R> the type of the result of the mapper function
 */
public interface Mappable<T, R> {
    /**
     * Maps the current entity to another type using the provided mapper function.
     *
     * @param mapper a function that converts an entity of type T to type R
     * @return the result of applying the mapper function to the current entity
     */
    R map(Function<? super T, ? extends R> mapper);
}