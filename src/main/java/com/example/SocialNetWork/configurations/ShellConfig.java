package com.example.SocialNetWork.configurations;

import com.example.SocialNetWork.models.aboutDiscussion.DiscussionEntity;
import com.example.SocialNetWork.models.aboutNote.NoteEntity;
import com.example.SocialNetWork.models.aboutUser.UserEntity;
import com.example.SocialNetWork.services.DiscussionService;
import com.example.SocialNetWork.services.NoteService;
import com.example.SocialNetWork.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellConfig {

    private final UserService userService;
    private final NoteService noteService;
    private final DiscussionService discussionService;

    @ShellMethod("Все пользователи")
    public void printAllUsers(){
        List<UserEntity> list = userService.findAll();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
    }
    @ShellMethod("Найти пользователя")
    public void findUserById(Long id){
        if(userService.findById(id) != null){
            userService.findById(id).toString();
        }
        else System.out.println("Пользователь не найден");
    }
    @ShellMethod("Удалить пользователя")
    public void deleteUser(Long id){
        if(userService.findById(id) != null){
            userService.deleteById(id);
            System.out.println("Пользователь удален");
        }
        else System.out.println("Пользователь не найден");
    }
    @ShellMethod("Найти запись")
    public void findNote(Long id){
        NoteEntity note = noteService.findById(id);
        System.out.println(note);
    }
    @ShellMethod("удалить запись")
    public void deleteNote(Long id){
        if(noteService.findById(id) != null){
            noteService.deleteNote(id);
            System.out.println("Запись удалена");
        }
        else System.out.println("Запись не найдена");
    }
    @ShellMethod("Найти обсуждение")
    public void findDiscussion(Long id){
        DiscussionEntity dis = discussionService.findById(id);
        System.out.println(dis);
    }
    @ShellMethod("удалить обсуждение")
    public void deleteDiscussion(Long id){
        if(discussionService.findById(id) != null){
            discussionService.deleteDiscussion(id);
            System.out.println("Обсуждение удалено");
        }
        else System.out.println("Обсуждение не найдено");
    }
}
