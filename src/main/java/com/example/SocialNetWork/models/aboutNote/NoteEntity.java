package com.example.SocialNetWork.models.aboutNote;

import com.example.SocialNetWork.models.aboutUser.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes")
public class NoteEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @NonNull
    @Column(name = "tag", columnDefinition = "TEXT")
    String tag;
    @NonNull
    @Column(name = "description", columnDefinition = "TEXT")
    String description;
    LocalDateTime createdAt;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    UserEntity user;
    @PrePersist
    private void init(){
        createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return id+" | "+tag+" | "+createdAt+" | "+"Автор с id: "+user.getId()+" ---------->\n";
    }
}
