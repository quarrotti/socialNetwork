package com.example.SocialNetWork.repositories;

import com.example.SocialNetWork.models.aboutUser.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
    List<UserEntity> findByUsername(String username);
}
