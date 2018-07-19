package com.sadi.toor.recommend.core.wrapper;

public class DataWrapper<T> {

    private T data;
    private ErrorObject error;

    public DataWrapper(T data, ErrorObject error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public ErrorObject getError() {
        return error;
    }
}
