package com.company.freeks.service;

import com.company.freeks.exceptions.*;
import com.company.freeks.repository.ProfileRepository;
import com.company.freeks.utils.PasswordEncoding;
import com.company.freeks.utils.ProfileValidation;
import com.company.freeks.dto.*;
import com.company.freeks.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoding passwordEncoding;

    @Transactional
    @Override
    public Profile createProfile(CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws ProfileAlreadyExistException, InvalidDiscordUsernameException, InvalidPasswordException, PasswordsDoNotMatchException, BlankFieldsException {
        ProfileValidation.checkIfFieldsAreFilled(createOrUpdateProfileRequest);
        String profileDiscordUsername = ProfileValidation.profileDiscordUsernameValidation(createOrUpdateProfileRequest);
        ProfileValidation.profilePasswordValidation(createOrUpdateProfileRequest);
        ProfileValidation.profilePasswordsMatchVerification(createOrUpdateProfileRequest);

        UUID profileId = UUID.randomUUID();

        String encryptedPassword = passwordEncoding.encoder().encode(createOrUpdateProfileRequest.getPassword());

        Profile newProfile = new Profile(profileId, profileDiscordUsername, createOrUpdateProfileRequest.getDiscordTag(), createOrUpdateProfileRequest.getDiscordName(), encryptedPassword);
        profilesId.add(profileId);

        profileRepository.save(newProfile);
        return newProfile;
    }

    @Override
    public Profile loginProfile(LoginProfileRequest loginProfileRequest) throws BadLoginCredentialsException {
        UUID loginProfileId = existsByDiscordUsername(loginProfileRequest.getDiscordUsername());

        if(loginProfileId != null) {
            Optional<Profile> loginProfileOptional = profileRepository.findById(loginProfileId);

            if(loginProfileOptional.isPresent()) {
                Profile loginProfileRepository = loginProfileOptional.get();

                if(BCrypt.checkpw(loginProfileRequest.getPassword(), loginProfileRepository.getPassword())) {
                    return loginProfileRepository;
                } else {
                    throw new BadLoginCredentialsException();
                }
            } else {
                throw new BadLoginCredentialsException();
            }
        } else {
            throw new BadLoginCredentialsException();
        }
    }

    @Override
    public Profile updateProfile(String profileDiscordUsername, CreateOrUpdateProfileRequest createOrUpdateProfileRequest) throws ProfileNotFoundException, ProfileAlreadyExistException, InvalidDiscordUsernameException {
        UUID profileToUpdateId = existsByDiscordUsername(profileDiscordUsername);

        if(profileToUpdateId != null) {
            Optional<Profile> profileToUpdateOptional = profileRepository.findById(profileToUpdateId);

            if (profileToUpdateOptional.isPresent()) {
                Profile profileToUpdate = profileToUpdateOptional.get();

                String newProfileDiscordUsername = ProfileValidation.profileDiscordUsernameValidation(createOrUpdateProfileRequest);

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