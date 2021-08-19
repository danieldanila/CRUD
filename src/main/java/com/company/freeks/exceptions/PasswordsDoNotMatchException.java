package com.company.freeks.exceptions;

public class PasswordsDoNotMatchException extends Exception {
    public PasswordsDoNotMatchException() {
        super("Error: Bad Request. The passwords do not match");
    }
}