package com.stc.stc.exceptions;

public class HttpClientErrorException extends RuntimeException{
    public HttpClientErrorException(String message) {
        super(message);
    }
}
