package com.as3j.messenger.dto;

import javax.validation.constraints.NotNull;

public class SingleValueDTO<T> {

    @NotNull
    private T value;

    public SingleValueDTO(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <T> SingleValueDTO<T> of(T value) {
        return new SingleValueDTO<>(value);
    }
}
