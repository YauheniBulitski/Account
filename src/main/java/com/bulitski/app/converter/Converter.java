package com.bulitski.app.converter;

@FunctionalInterface
public interface Converter<T, R> {
    R convert(T objects);
}
