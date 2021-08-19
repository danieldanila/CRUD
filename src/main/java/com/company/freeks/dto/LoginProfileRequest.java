package com.company.freeks.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginProfileRequest implements Serializable {
    private String discordUsername;
    private String password;
}
