package com.example.x2037.freeks.Service;

import com.example.x2037.freeks.Repository.ProfileRepository;
import com.example.x2037.freeks.Utils.ProfileValidation;
import com.example.x2037.freeks.dto.*;
import com.example.x2037.freeks.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final Set<UUID> profilesId = new HashSet<>();

    @Autowired
    ProfileRepository profileRepository;

    @Transactional
    @Override
    public Profile createProfile(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws ProfileAlreadyExistException, InvalidDiscordUsernameException, ProfileNotFoundException {
        String profileDiscordUsername = ProfileValidation.profileValidation(createOrUpdateProfileRequest);

        UUID profileId = UUID.randomUUID();
        Profile newProfile = new Profile(profileId, profileDiscordUsername, createOrUpdateProfileRequest.getDiscordTag(), createOrUpdateProfileRequest.getDiscordName());
        profilesId.add(profileId);

        profileRepository.save(newProfile);
        return newProfile;
    }

    @Override
    public Profile updateProfile(String profileDiscordUsername, CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws ProfileNotFoundException, ProfileAlreadyExistException, InvalidDiscordUsernameException {
        UUID profileToUpdateId = existsByDiscordUsername(profileDiscordUsername);

        if(profileToUpdateId != null) {
            Optional<Profile> profileToUpdateOptional = profileRepository.findById(profileToUpdateId);

            if (profileToUpdateOptional.isPresent()) {
                Profile profileToUpdate = profileToUpdateOptional.get();

                String newProfileDiscordUsername = ProfileValidation.profileValidation(createOrUpdateProfileRequest);

                profileToUpdate.setDiscordName(createOrUpdateProfileRequest.getDiscordName());
                profileToUpdate.setDiscordTag(createOrUpdateProfileRequest.getDiscordTag());
                profileToUpdate.setDiscordUsername(newProfileDiscordUsername);
                profileRepository.save(profileToUpdate);

                return profileToUpdate;
            } else {
                throw new ProfileNotFoundException(createOrUpdateProfileRequest.getDiscordUsername());
            }
        } else {
            throw new InvalidDiscordUsernameException(profileDiscordUsername);
        }
    }

    @Override
    public Profile getProfile(String profileDiscordUsername) throws ProfileNotFoundException, InvalidDiscordUsernameException {
        UUID profileToGetId = existsByDiscordUsername(profileDiscordUsername);

        if(profileToGetId != null ) {
            Optional<Profile> profileToGetOptional = profileRepository.findById(profileToGetId);

            if (profileToGetOptional.isPresent()) {
                return profileToGetOptional.get();
            } else {
                throw new ProfileNotFoundException(profileDiscordUsername);
            }
        } else {
            throw new InvalidDiscordUsernameException(profileDiscordUsername);
        }
    }

    @Override
    public void deleteProfile(String profileDiscordUsername) throws ProfileNotFoundException, InvalidDiscordUsernameException {
        UUID profileToDeleteId = existsByDiscordUsername(profileDiscordUsername);

        if(profileToDeleteId != null) {
            Optional<Profile> profileToDeleteOptional = profileRepository.findById(profileToDeleteId);

            if (profileToDeleteOptional.isPresent()) {
                profileRepository.deleteById(profileToDeleteId);
            } else {
                throw new ProfileNotFoundException(profileDiscordUsername);
            }
        } else {
            throw new InvalidDiscordUsernameException(profileDiscordUsername);
        }
    }

    @Override
    public UUID existsByDiscordUsername(String profileDiscordUsername) {
        for (UUID profileId: profilesId) {
            Optional<Profile> checkedProfileOptional = profileRepository.findById(profileId);
            if(checkedProfileOptional.isPresent()) {
               Profile checkedProfile = checkedProfileOptional.get();

                if(checkedProfile.getDiscordUsername().equals(profileDiscordUsername)) {
                    return profileId;
                }
            }
        }
        return null;
    }
}