package com.company.exceptions;

public class LackOfRoomException extends RuntimeException{
    public LackOfRoomException(String message) {
        super(message);
    }
}
