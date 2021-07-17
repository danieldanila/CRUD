package com.example.x2037.freeks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Profile {
    @Id
    @Column(name = "profile_id", nullable = false)
    private UUID profileId;
    @Column(name = "discord_username", nullable = false)
    private String discordUsername;
    @Column(name = "discord_tag", nullable = false)
    private String discordTag;
    @Column(name = "discord_name", nullable = false)
    private String discordName;
    @Column(name = "books_read", nullable = false)
    private  int booksRead = 0;
    @Column(name = "member_since", nullable = false)
    private final int memberSince = Calendar.getInstance().get(Calendar.YEAR);;

    public Profile() {

    }

    public Profile(UUID profileId, String discordUsername, String discordTag, String discordName) {
        this.profileId = profileId;
        this.discordUsername = discordUsername;
        this.discordTag = discordTag;
        this.discordName = discordName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return booksRead == profile.booksRead && memberSince == profile.memberSince && profileId.equals(profile.profileId) && discordUsername.equals(profile.discordUsername) && discordTag.equals(profile.discordTag) && discordName.equals(profile.discordName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, discordUsername, discordTag, discordName, booksRead, memberSince);
    }
}