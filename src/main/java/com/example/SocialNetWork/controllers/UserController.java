package com.example.SocialNetWork.controllers;

import com.example.SocialNetWork.models.aboutDiscussion.DiscussionEntity;
import com.example.SocialNetWork.models.aboutNote.NoteEntity;
import com.example.SocialNetWork.models.aboutUser.UserEntity;
import com.example.SocialNetWork.repositories.UserRepository;
import com.example.SocialNetWork.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    @PostMapping("/registration")
    public String registrationP(UserEntity userEntity){
        userService.createUser(userEntity);
        return "redirect:/hello";
    }
    @GetMapping("/hello")
    public String securityUrl() {
        return "commonPages/Registry&Login/hello";
    }
    @GetMapping("/users/personalProfile")
    public String openPersonalProfile(Principal principal, Model model){
        UserEntity currentUser = userRepository.findByLogin(principal.getName()).orElse(null);
        List<NoteEntity> listNotes = userService.listNotesOfCurrentUser(principal);
        List<DiscussionEntity> listDis = userService.listDiscussionsOfCurrentUser(principal);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("listNotes",listNotes);
        model.addAttribute("listDiscussions", listDis);
        return "userPages/personalProfile";
    }
    @GetMapping("/users/personalProfile/edit")
    public String editUserProfile(Principal principal){ //model
//        UserEntity user  = userRepository.findByLogin(principal.getName()).orElse(null);
//        model.addAttribute("currentUser ", user);
        return "userPages/updateUser";
    }
    @PostMapping("/users/personalProfile/edit")
    public String editUserProfileP(@RequestParam(required = false) String username,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) String description,  Principal principal){
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setDescription(description);
        userService.editUser(user, principal);
        return "redirect:/users/personalProfile";
    }

    @GetMapping("/searchUsers")
    public String searchUser(@RequestParam String username, Model model){
        model.addAttribute("listUsers", userService.getListByUsername(username));
        return "userPages/searchUsers"; // todo
    }
    @GetMapping("/users")
    public String allUsers(Model model){
        model.addAttribute("listOfAllUsers", userService.findAll());
        return "userPages/allUsers";
    }

    @GetMapping("/users/{id}")
    public String checkAnyProfile(@PathVariable Long id,Model model){
        model.addAttribute("profile", userService.findById(id));
        model.addAttribute("listNotesForUser",userService.listNotesOfCurrentUser(id));
        model.addAttribute("listDiscussions", userService.listDiscussionsOfCurrentUser(id));
        return "userPages/profileForAnybody";
    }

    @PostMapping("/users/personalProfile/edit/addImg") //не реализовано представление
    public String addImgToUser(Principal principal, @RequestParam("file") MultipartFile file){
        try {
            userService.addImgToUser(principal.getName(), file);
            return "redirect:/users/personalProfile";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
