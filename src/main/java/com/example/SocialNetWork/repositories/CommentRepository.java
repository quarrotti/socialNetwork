package com.example.SocialNetWork.repositories;

import com.example.SocialNetWork.models.aboutDiscussion.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAlLByDiscussionId(Long id);
}
