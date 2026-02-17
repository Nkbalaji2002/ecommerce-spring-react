package com.social.media;

import com.social.media.model.Post;
import com.social.media.model.SocialGroup;
import com.social.media.model.SocialProfile;
import com.social.media.model.SocialUser;
import com.social.media.repository.PostRepository;
import com.social.media.repository.SocialGroupRepository;
import com.social.media.repository.SocialProfileRepository;
import com.social.media.repository.SocialUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final SocialUserRepository userRepository;
    private final SocialGroupRepository groupRepository;
    private final SocialProfileRepository profileRepository;
    private final PostRepository postRepository;

    public DataInitializer(SocialUserRepository userRepository, SocialGroupRepository groupRepository, SocialProfileRepository profileRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // create some users
            SocialUser user1 = new SocialUser();
            SocialUser user2 = new SocialUser();
            SocialUser user3 = new SocialUser();

            // save users to the database
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            // create some groups
            SocialGroup group1 = new SocialGroup();
            SocialGroup group2 = new SocialGroup();

            // Add users to groups
            group1.getSocialUsers().add(user1);
            group1.getSocialUsers().add(user2);

            group2.getSocialUsers().add(user2);
            group2.getSocialUsers().add(user3);

            // Save groups to the database
            groupRepository.save(group1);
            groupRepository.save(group2);

            // associate users with groups
            user1.getGroups().add(group1);
            user2.getGroups().add(group1);
            user2.getGroups().add(group2);
            user3.getGroups().add(group2);

            // save users back to database to update associations
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            // create some posts
            Post post1 = new Post();
            Post post2 = new Post();
            Post post3 = new Post();

            // Associate posts with users
            post1.setSocialUser(user1);
            post2.setSocialUser(user2);
            post3.setSocialUser(user3);

            // save posts to the database (assuming you have a post repository)
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            // create some social profiles
            SocialProfile profile1 = new SocialProfile();
            SocialProfile profile2 = new SocialProfile();
            SocialProfile profile3 = new SocialProfile();

            // associate profiles with users
            profile1.setUser(user1);
            profile2.setUser(user2);
            profile3.setUser(user3);

            // save profiles to the database (assuming you have a Social Profile Repository)
            profileRepository.save(profile1);
            profileRepository.save(profile2);
            profileRepository.save(profile3);

        };
    }
}
