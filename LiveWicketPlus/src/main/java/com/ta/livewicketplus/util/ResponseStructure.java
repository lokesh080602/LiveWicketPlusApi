package com.ta.livewicketplus.util;

import java.util.List;


public class ResponseStructure<T> {

    private int status;
    private String message;
    private T data; // Changed from Object to generic type T

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T players) {
        this.data = players;
    }
}
