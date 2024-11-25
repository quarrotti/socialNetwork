package com.example.SocialNetWork.repositories;

import com.example.SocialNetWork.models.aboutUser.UserImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImageEntity, Long> {
}
