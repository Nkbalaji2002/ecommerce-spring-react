package com.social.media.service;

import com.social.media.model.SocialUser;
import com.social.media.repository.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialService {

    @Autowired
    SocialUserRepository socialUserRepository;

    public List<SocialUser> getAllUsers() {
        return socialUserRepository.findAll();
    }

    public SocialUser saveUssr(SocialUser socialUser) {
        return socialUserRepository.save(socialUser);
    }

}
