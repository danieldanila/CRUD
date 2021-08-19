package com.company.freeks.controller;

import com.company.freeks.exceptions.*;
import com.company.freeks.service.ProfileService;
import com.company.freeks.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfieControllerImpl implements ProfileController {

    @Autowired
    ProfileService profileService;

    @Override
    @PostMapping("/register")
        public ResponseEntity<Object> createProfile(@RequestBody CreateOrUpdateProfileRequest createOrUpdateProfileRequest) {
        try {
            return ResponseEntity.ok(profileService.createProfile(createOrUpdateProfileRequest));
        } catch(InvalidDiscordUsernameException | ProfileAlreadyExistException | InvalidPasswordException | PasswordsDoNotMatchException | BlankFieldsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<Object> loginProfile(@RequestBody LoginProfileRequest loginProfileRequest) {
        try {
            return ResponseEntity.ok(profileService.loginProfile(loginProfileRequest));
        } catch (BadLoginCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PutMapping("/profile/{profileDiscordUsername}")
    public ResponseEntity<Object> updateProfile(@RequestBody CreateOrUpdateProfileRequest createOrUpdateProfileRequest, @PathVariable("profileDiscordUsername") String profileDiscordUsername) {
        try {
            return ResponseEntity.ok(profileService.updateProfile(profileDiscordUsername, createOrUpdateProfileRequest));
        } catch (ProfileNotFoundException | ProfileAlreadyExistException | InvalidDiscordUsernameException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Override
    @GetMapping("/profile/{profileDiscordUsername}")
    public ResponseEntity<Object> getProfile(@PathVariable("profileDiscordUsername") String profileDiscordUsername) {
        try {
            return ResponseEntity.ok(profileService.getProfile(profileDiscordUsername));
        } catch (ProfileNotFoundException | InvalidDiscordUsernameException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Override
    @DeleteMapping("profile/{profileDiscordUsername}")
    public ResponseEntity<Object> deleteProfile(@PathVariable("profileDiscordUsername") String profileDiscordUsername) {
        try {
            profileService.deleteProfile(profileDiscordUsername);
            return ResponseEntity.ok().body("Profile with the discord username \"" + profileDiscordUsername + "\" was successfully deleted.");
        } catch (ProfileNotFoundException | InvalidDiscordUsernameException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}