package com.powerofwear.exception_handler;




public record ErrorResponse(int status, String message) {
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
