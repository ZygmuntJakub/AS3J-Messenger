package com.as3j.messenger.dto;

public class SingleValueDTO<T> {
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
