package com.example.SocialNetWork.services;

import com.example.SocialNetWork.models.aboutNote.NoteEntity;
import com.example.SocialNetWork.models.aboutUser.UserEntity;
import com.example.SocialNetWork.repositories.NoteRepository;
import com.example.SocialNetWork.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public List<NoteEntity> listNotes(){
        if(noteRepository.findAll().isEmpty()) return new ArrayList<>();
        return noteRepository.findAll();
    }
    @CachePut(value = "noteCache")
    public void saveNote(Principal principal, NoteEntity note){
        note.setUser(getUserByPrincipal(principal));
        noteRepository.save(note);
    }
    public void editNote(NoteEntity note, Principal principal){
        NoteEntity updatedNote = new NoteEntity();
        if (updatedNote.getUser().getLogin().equals(principal.getName())) {
            if(note.getTag() != "") updatedNote.setTag(note.getTag());
            if(note.getDescription() != "") updatedNote.setDescription(note.getDescription());
            saveNote(principal, updatedNote);
        }
    }
    public UserEntity getUserByPrincipal(Principal principal) {
        return userRepository.findByLogin(principal.getName()).orElse(new UserEntity());
    }
    @Transactional
    @CacheEvict(value = "noteCache", key = "#id")
    public void deleteNote(Long id){
        noteRepository.deleteById(id);
    }
    //@Cacheable(value = "noteCache", key = "#id", unless = "#result == null")
    public NoteEntity findById(Long id){
        return noteRepository.findById(id).orElse(null);
    }

    public List<NoteEntity> getListByTag(String tag){
        return noteRepository.findByTag(tag);
    }
}
