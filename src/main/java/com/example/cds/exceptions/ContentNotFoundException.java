package com.example.cds.exceptions;

public class ContentNotFoundException extends Exception{
    public ContentNotFoundException(String message) {
        super(message);
    }

    public ContentNotFoundException() {
    }
}
