package com.example.x2037.freeks.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CreateOrUpdateProfileRequest implements Serializable {
    private String discordTag;
    private String discordName;
    private String discordUsername;
}