package com.example.SocialNetWork.services;

import com.example.SocialNetWork.models.aboutDiscussion.DiscussionEntity;
import com.example.SocialNetWork.models.aboutUser.UserEntity;
import com.example.SocialNetWork.repositories.DiscussionRepository;
import com.example.SocialNetWork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;
    public UserEntity getUserByPrincipal(Principal principal) {
        return userRepository.findByLogin(principal.getName()).orElse(new UserEntity());
    }
    public List<DiscussionEntity> allDiscussions(){
        if(discussionRepository.findAll().isEmpty()) return new ArrayList<>(0);
        return discussionRepository.findAll();
    }
    @CachePut(value = "discussionCache")
    public void createDiscussion(Principal principal, DiscussionEntity discussion){
        discussion.setUser(getUserByPrincipal(principal));
        discussionRepository.save(discussion);
    }
    @CacheEvict(value = "discussionCache")
    public void deleteDiscussion(Long id){
        discussionRepository.deleteById(id);
    }
    @Cacheable(value = "discussionCache")
    public DiscussionEntity findById(Long id){
        return discussionRepository.findById(id).orElse(null);
    }
    public List<DiscussionEntity> getListByHeading(String heading){
        return discussionRepository.findByHeading(heading);
    }

}
