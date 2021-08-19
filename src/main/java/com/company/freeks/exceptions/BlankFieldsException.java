package com.company.freeks.exceptions;

public class BlankFieldsException extends Exception {
    public BlankFieldsException() {
        super("Error: Bad Request. There are blank fields.");
    }
}