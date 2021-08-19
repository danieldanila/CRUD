package com.company.freeks.exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("Error: Bad Request. The password is invalid. A Password must have: " +
                "minimum eight characters, at least one uppercase letter, " +
                "one lowercase letter, one number and one special character");
    }
}