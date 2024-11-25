package com.example.SocialNetWork.models.aboutUser;

import com.example.SocialNetWork.models.aboutDiscussion.DiscussionEntity;
import com.example.SocialNetWork.models.aboutNote.NoteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String login;
    String password;
    String username;
    String email;
    @Column(name = "description", columnDefinition = "TEXT")
    String description;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    Collection<Role> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    List<NoteEntity> notes = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    List<DiscussionEntity> discussions;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    List<UserImageEntity> images;

    @Override
    public String toString() {
        return id+" | "+login+" | "+username+" | "+password+" ---------->\n";
    }
}
