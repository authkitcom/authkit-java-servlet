package com.authkit;

public final class Util {

    static final <T> T orDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    static final <T> T required(T value, String label) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("%s is required", label));
        }
        return value;
    }
}
