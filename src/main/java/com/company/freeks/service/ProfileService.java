package com.company.freeks.service;

import com.company.freeks.dto.*;
import com.company.freeks.exceptions.*;
import com.company.freeks.model.Profile;

import java.util.UUID;

public interface ProfileService {
    Profile createProfile(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws ProfileAlreadyExistException, InvalidDiscordUsernameException, InvalidPasswordException, PasswordsDoNotMatchException, BlankFieldsException;
    Profile loginProfile(LoginProfileRequest loginProfileRequest) throws BadLoginCredentialsException;
    Profile updateProfile(String profileDiscordTag, CreateOrUpdateProfileRequest updateProfileRequest) throws ProfileNotFoundException, ProfileAlreadyExistException, InvalidDiscordUsernameException;
    Profile getProfile(String profileDiscordUsername) throws ProfileNotFoundException, InvalidDiscordUsernameException;
    void deleteProfile(String profileDiscordUsername) throws ProfileNotFoundException, InvalidDiscordUsernameException;
    UUID existsByDiscordUsername(String profileDiscordUsername);
}