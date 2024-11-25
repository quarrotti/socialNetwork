package com.example.SocialNetWork.models.aboutUser;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String fileName;
    String fileType;
    @Lob
    byte[] data;
    @ManyToOne
    @JoinColumn
    UserEntity user;
}
