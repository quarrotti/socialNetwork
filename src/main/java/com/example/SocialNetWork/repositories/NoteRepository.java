package com.example.SocialNetWork.repositories;

import com.example.SocialNetWork.models.aboutNote.NoteEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findByTag(String tag);
    void deleteByTag(String tag);

    Optional<NoteEntity> findById(Long id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM notes WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    List<NoteEntity> findByUser(Principal principal);


}
