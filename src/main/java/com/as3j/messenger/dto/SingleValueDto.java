package com.as3j.messenger.dto;

import javax.validation.constraints.NotNull;

public class SingleValueDto<T> {

    @NotNull
    private T value;

    public SingleValueDto(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <T> SingleValueDto<T> of(T value) {
        return new SingleValueDto<>(value);
    }
}
