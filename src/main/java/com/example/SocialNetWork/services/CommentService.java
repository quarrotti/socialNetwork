package com.example.SocialNetWork.services;

import com.example.SocialNetWork.models.aboutDiscussion.CommentEntity;
import com.example.SocialNetWork.models.aboutUser.UserEntity;
import com.example.SocialNetWork.repositories.CommentRepository;
import com.example.SocialNetWork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    public UserEntity getUserByPrincipal(Principal principal) {
        return userRepository.findByLogin(principal.getName()).orElse(new UserEntity());
    }
    public List<CommentEntity> allCommentsDis(Long id){
        if(commentRepository.findAlLByDiscussionId(id).isEmpty()) return new ArrayList<>();
        return commentRepository.findAlLByDiscussionId(id);
    }

    public void createComment(Principal principal, CommentEntity comment){
        comment.setUser(getUserByPrincipal(principal));
        commentRepository.save(comment);
    }
}
