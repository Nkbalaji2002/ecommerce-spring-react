package com.social.media.repository;

import com.social.media.model.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
}
