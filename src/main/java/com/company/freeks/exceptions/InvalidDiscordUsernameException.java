package com.company.freeks.exceptions;

public class InvalidDiscordUsernameException extends Exception {
    public InvalidDiscordUsernameException(String profileDiscordName, String profileDiscordTag) {
        super("Error: Bad Request. The discord username \"" + profileDiscordName + "#" + profileDiscordTag + "\" is invalid.");
    }

    public InvalidDiscordUsernameException(String profileDiscordUsername) {
        super("Error: Bad Request. The discord username \"" + profileDiscordUsername + "\" is invalid.");
    }
}