package com.example.SocialNetWork.repositories;

import com.example.SocialNetWork.models.aboutDiscussion.DiscussionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiscussionRepository extends JpaRepository<DiscussionEntity, Long> {
    Optional<DiscussionEntity> findById(Long id);
    List<DiscussionEntity> findAll();
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM discussion WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);
    List<DiscussionEntity> findByHeading(String heading);

}
