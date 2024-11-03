package com.ericafenyo.seniorhub.api;

import java.util.function.Function;

public interface Mappable<T, R> {
    R map(Function<? super T, ? extends R> mapper);
}
