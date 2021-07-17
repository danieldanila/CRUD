package com.example.x2037.freeks.dto;

public class ProfileNotFoundException extends Exception {

    public ProfileNotFoundException(String profileDiscordUsername) {
        super("Error: Bad Request. The profile with the discord username: \"" + profileDiscordUsername + "\" doesn't exists.");
    }
}