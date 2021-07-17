package com.example.x2037.freeks.Utils;

import com.example.x2037.freeks.Service.ProfileService;
import com.example.x2037.freeks.dto.CreateOrUpdateProfileRequest;
import com.example.x2037.freeks.dto.InvalidDiscordUsernameException;
import com.example.x2037.freeks.dto.ProfileAlreadyExistException;
import com.example.x2037.freeks.dto.ProfileNotFoundException;
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

    public static String profileValidation(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws ProfileAlreadyExistException, InvalidDiscordUsernameException, ProfileNotFoundException {
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
}