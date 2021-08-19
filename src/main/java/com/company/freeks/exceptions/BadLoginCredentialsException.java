package com.company.freeks.exceptions;

public class BadLoginCredentialsException extends Exception{

    public BadLoginCredentialsException() {
        super("The discord username or password is incorrect.");
    }
}
