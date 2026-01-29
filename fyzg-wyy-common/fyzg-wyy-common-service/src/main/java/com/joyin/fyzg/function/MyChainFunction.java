package com.joyin.fyzg.function;



import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface MyChainFunction<T, R> {

    R apply();

    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply());
    }
}
