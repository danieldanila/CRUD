package com.example.x2037.freeks.Service;

import com.example.x2037.freeks.dto.*;
import com.example.x2037.freeks.model.Profile;

import java.util.UUID;

public interface ProfileService {
    Profile createProfile(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws ProfileAlreadyExistException, InvalidDiscordUsernameException, ProfileNotFoundException;
    Profile updateProfile(String profileDiscordTag, CreateOrUpdateProfileRequest updateProfileRequest) throws ProfileNotFoundException, ProfileAlreadyExistException, InvalidDiscordUsernameException;
    Profile getProfile(String profileDiscordUsername) throws ProfileNotFoundException, InvalidDiscordUsernameException;
    void deleteProfile(String profileDiscordUsername) throws ProfileNotFoundException, InvalidDiscordUsernameException;
    UUID existsByDiscordUsername(String profileDiscordUsername);
}