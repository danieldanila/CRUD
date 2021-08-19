package com.company.freeks.controller;

import com.company.freeks.dto.CreateOrUpdateProfileRequest;
import com.company.freeks.dto.LoginProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProfileController {
    ResponseEntity<Object> createProfile(@RequestBody CreateOrUpdateProfileRequest createOrUpdateProfileRequest);
    ResponseEntity<Object> loginProfile(@RequestBody LoginProfileRequest loginProfileRequest);
    ResponseEntity<Object> updateProfile(@RequestBody CreateOrUpdateProfileRequest createOrUpdateProfileRequest, @PathVariable("profileDiscordTag") String profileDiscordTag);
    ResponseEntity<Object> getProfile(@PathVariable("profileDiscordUsername") String profileDiscordUsername);
    ResponseEntity<Object> deleteProfile(@PathVariable("profileDiscordUsername") String profileDiscordUsername);
}