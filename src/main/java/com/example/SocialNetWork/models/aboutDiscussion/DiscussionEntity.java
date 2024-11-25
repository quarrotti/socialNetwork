package com.example.SocialNetWork.models.aboutDiscussion;

import com.example.SocialNetWork.models.aboutUser.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discussion")
public class DiscussionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column(name = "heading", columnDefinition = "TEXT")
    String heading;
    @Column(name = "body", columnDefinition = "TEXT")
    String body;
    LocalDateTime createdAt;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    UserEntity user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    List<CommentEntity> commentEntity;
    @PrePersist
    private void init(){
        createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return id + " | " + heading + " | " + createdAt + " | " + "Автор с id: " + user.getId() + " ---------->\n";
    }
}
