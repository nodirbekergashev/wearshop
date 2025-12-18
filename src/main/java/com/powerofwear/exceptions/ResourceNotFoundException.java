package com.powerofwear.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String type, Long id) {
        super(String.format("%s not found with id: %s", type, id));
    }
}
