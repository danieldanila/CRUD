package com.company.freeks.utils;

import com.company.freeks.exceptions.*;
import com.company.freeks.service.ProfileService;
import com.company.freeks.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProfileValidation {

    private static ProfileService profileService;

    @Autowired
    ProfileService profileService0;

    @PostConstruct
    private void initStaticProfileService() {
        profileService = this.profileService0;
    }

    public static void checkIfFieldsAreFilled(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws BlankFieldsException {
        if(createOrUpdateProfileRequest.getDiscordName() == null || createOrUpdateProfileRequest.getDiscordTag() == null || createOrUpdateProfileRequest.getPassword() == null || createOrUpdateProfileRequest.getConfirmPassword() == null) {
            throw new BlankFieldsException();
        }

    }
    public static String profileDiscordUsernameValidation(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws ProfileAlreadyExistException, InvalidDiscordUsernameException {
        if (createOrUpdateProfileRequest.getDiscordTag().length() == 4 && createOrUpdateProfileRequest.getDiscordTag().matches("[0-9]+") && createOrUpdateProfileRequest.getDiscordName().length() >= 2 && createOrUpdateProfileRequest.getDiscordName().length() <= 32) {
            String profileDiscordUsername = createOrUpdateProfileRequest.getDiscordName() + "#" + createOrUpdateProfileRequest.getDiscordTag();

            if (profileService.existsByDiscordUsername(profileDiscordUsername) != null) {
                throw new ProfileAlreadyExistException(profileDiscordUsername);
            }

            return profileDiscordUsername;
        } else {
            throw new InvalidDiscordUsernameException(createOrUpdateProfileRequest.getDiscordName(), createOrUpdateProfileRequest.getDiscordTag());
        }
    }

    public static void profilePasswordValidation(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws InvalidPasswordException {
        if(!createOrUpdateProfileRequest.getPassword().matches("((?=.*\\d)(?=.*[A-Z])(?=.*\\W).{8,})")) { // Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
            throw new InvalidPasswordException();
        }
    }
    
    public static void profilePasswordsMatchVerification(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws PasswordsDoNotMatchException {
        if (!createOrUpdateProfileRequest.getPassword().equals(createOrUpdateProfileRequest.getConfirmPassword())) {
            throw new PasswordsDoNotMatchException();
        }
    }
}