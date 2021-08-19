package com.company.freeks.exceptions;

public class ProfileAlreadyExistException extends Exception {
    public ProfileAlreadyExistException(String profileDiscordUsername) {
        super("Error: Bad Request. The profile with the discord username: \"" + profileDiscordUsername + "\" already exists.");
    }
}