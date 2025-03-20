package com.example.exception;

public class InvalidLoginException extends Exception {
   
    public InvalidLoginException(String msg) {
        super(msg);
    }

}
