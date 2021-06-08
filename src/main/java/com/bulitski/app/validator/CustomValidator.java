package com.bulitski.app.validator;

import org.springframework.core.GenericTypeResolver;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface CustomValidator<T> extends Validator {

    @Override
    default boolean supports(Class<?> clazz) {
        Class<?> currentClazz = GenericTypeResolver.resolveTypeArgument(getClass(), CustomValidator.class);
        return currentClazz.isAssignableFrom(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    default void validate(Object target, Errors errors) {
        check((T) target, errors);
    }

    void check(T target, Errors errors);
}
