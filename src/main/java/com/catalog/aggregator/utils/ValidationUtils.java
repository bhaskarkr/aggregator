package com.catalog.aggregator.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public final class ValidationUtils {
    public static boolean isInValidId(String id) {
        return Objects.isNull(id) || id.isBlank() || id.indexOf('_') != -1;
    }
}
