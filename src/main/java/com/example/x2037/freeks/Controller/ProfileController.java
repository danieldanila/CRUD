package com.example.x2037.freeks.Controller;

import com.example.x2037.freeks.dto.CreateOrUpdateProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProfileController {
    ResponseEntity<Object> createProfile(@RequestBody CreateOrUpdateProfileRequest createOrUpdateProfileRequest);
    ResponseEntity<Object> updateProfile(@RequestBody CreateOrUpdateProfileRequest createOrUpdateProfileRequest, @PathVariable("profileDiscordTag") String profileDiscordTag);
    ResponseEntity<Object> getProfile(@PathVariable("profileDiscordUsername") String profileDiscordUsername);
    ResponseEntity<Object> deleteProfile(@PathVariable("profileDiscordUsername") String profileDiscordUsername);
}