package com.company.exceptions;

public class NotExistingDirectoryException extends RuntimeException {
    public NotExistingDirectoryException(String message) {
        super(message);
    }
}
