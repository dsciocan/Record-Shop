package com.northcoders.record_shop.exception;

public class InvalidItemException extends IllegalArgumentException{
    public InvalidItemException(String message) {
        super(message);
    }

}
