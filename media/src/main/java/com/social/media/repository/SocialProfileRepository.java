package com.social.media.repository;

import com.social.media.model.SocialProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialProfileRepository extends JpaRepository<SocialProfile, Long> {
}
