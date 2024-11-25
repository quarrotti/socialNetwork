package com.example.SocialNetWork.services;

import com.example.SocialNetWork.models.aboutDiscussion.DiscussionEntity;
import com.example.SocialNetWork.models.aboutNote.NoteEntity;
import com.example.SocialNetWork.models.aboutUser.Role;
import com.example.SocialNetWork.models.aboutUser.UserEntity;
import com.example.SocialNetWork.models.aboutUser.UserImageEntity;
import com.example.SocialNetWork.repositories.UserImageRepository;
import com.example.SocialNetWork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserImageRepository userImageRepository;
    public UserEntity createUser(UserEntity user) {
        user.setLogin(user.getLogin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.User);
        return userRepository.save(user);
    }
    public void editUser(UserEntity user, Principal principal){
        UserEntity updateUser = findByLogin(principal.getName());
        if(user.getUsername() != "") updateUser.setUsername(user.getUsername());
        if(user.getEmail() != "") updateUser.setEmail(user.getEmail());
        if(user.getDescription() != "") updateUser.setDescription(user.getDescription());
        userRepository.save(updateUser); //todo перенести в сервис
    }

    public List<NoteEntity> listNotesOfCurrentUser(Principal principal){
        UserEntity user = userRepository.findByLogin(principal
                .getName())
                .orElseThrow(() -> new RuntimeException("User  not found"));
        return user.getNotes();
        // todo перенести в noteservice
    }
    public List<DiscussionEntity> listDiscussionsOfCurrentUser(Principal principal){
        UserEntity user = userRepository.findByLogin(principal
                .getName())
                .orElseThrow(() -> new RuntimeException("User  not found"));
        return user.getDiscussions(); //todo перенести в discussionService
    }
    public List<DiscussionEntity> listDiscussionsOfCurrentUser(Long id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User  not found"));
        return user.getDiscussions(); //todo перенести в discussionService
    }
    public UserEntity findByLogin(String login){
        return userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User  not found"));
    }
    public List<UserEntity>getListByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public UserEntity findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User  not found"));
    }
    public List<NoteEntity> listNotesOfCurrentUser(Long id){
        UserEntity user = userRepository.findById(id).orElse(new UserEntity());
        return user.getNotes();
        // todo перенести в noteservice
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public void addImgToUser(String login, MultipartFile file) throws IOException {
        UserEntity user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User  not found"));

        UserImageEntity image = new UserImageEntity();

        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setData(file.getBytes());
        image.setUser(user);

        user.getImages().add(image);

        userRepository.save(user);
    }
}
//orElseThrow(() -> new RuntimeException("User  not found")); todo