package com.example.x2037.freeks.Repository;

import com.example.x2037.freeks.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, UUID> {
}