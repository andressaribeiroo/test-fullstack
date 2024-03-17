package com.uol.backend.Services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
