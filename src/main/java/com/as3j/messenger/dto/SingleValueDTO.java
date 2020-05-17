package com.as3j.messenger.dto;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;

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
        return new SingleValueDTO<T>(value);
    }
}
