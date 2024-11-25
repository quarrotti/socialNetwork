package com.example.SocialNetWork.controllers;

import com.example.SocialNetWork.models.aboutDiscussion.CommentEntity;
import com.example.SocialNetWork.models.aboutDiscussion.DiscussionEntity;
import com.example.SocialNetWork.services.CommentService;
import com.example.SocialNetWork.services.DiscussionService;
import com.example.SocialNetWork.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DiscussionController {
    private final DiscussionService discussionService;
    private final CommentService commentService;
    private final UserService userService;
    @GetMapping("/discussions")
    public String all(Model model){
        List<DiscussionEntity> listDiscussions = discussionService.allDiscussions();
        model.addAttribute("listDiscussions", listDiscussions);
        return "disPages/allDis";
    }
    @GetMapping("/discussions/{id}")
    public String discussionInfo(@PathVariable Long id, Model model){
        DiscussionEntity dis = discussionService.findById(id);
        List<CommentEntity> comments = commentService.allCommentsDis(id);
        model.addAttribute("dis", dis);
        model.addAttribute("comments", comments);
        return "disPages/currentDis";
    }
    @GetMapping("/discussions/personalDis/{id}")
    public String personalDiscussionInfo(@PathVariable Long id, Model model){
        DiscussionEntity dis = discussionService.findById(id);
        List<CommentEntity> comments = commentService.allCommentsDis(id);
        model.addAttribute("dis", dis);
        model.addAttribute("comments", comments);
        return "disPages/personalDisPage";
    }
    @PostMapping("/discussion/delete/{id}")
    public String deleteDis(@PathVariable Long id, Principal principal){
        if(userService.findByLogin(principal.getName()).getUsername().equals(discussionService.findById(id).getUser().getUsername())){
            discussionService.deleteDiscussion(id);
            return "commonPages/successDelete";
        }
        return "redirect:/fail";
    }
    @PostMapping("/discussions/createDiscussion")
    public String createDiscussion(Principal principal, DiscussionEntity dis){
        discussionService.createDiscussion(principal,dis);
        return "redirect:/discussions";
    }
    @PostMapping("/discussions/createCommentForDiscussionWithId/{id}")
    public String createComment(@PathVariable Long id,Principal principal, CommentEntity comment){
        comment.setDiscussion(discussionService.findById(id));
        commentService.createComment(principal,comment);
        return "redirect:/discussions/"+id;
    }
    @GetMapping("/discussions/edit/{id}")
    public String editNoteForm(@PathVariable Long id, Principal principal, Model model) {
        DiscussionEntity dis = discussionService.findById(id);
        if (dis.getUser().getLogin().equals(principal.getName())) {
            model.addAttribute("dis", dis);
            return "disPages/updateDiscussion";
        }
        return "redirect:/notes-feed";
    }

    @PostMapping("/discussions/edit/{id}")
    public String updateNote(@PathVariable Long id, DiscussionEntity dis, Principal principal) {
        DiscussionEntity updatedDiscussion = discussionService.findById(id);
        // Проверяем, является ли текущий пользователь создателем dis
        if (updatedDiscussion.getUser().getLogin().equals(principal.getName())) {
            if(dis.getHeading() != null) updatedDiscussion.setHeading(dis.getHeading());
            if(dis.getBody() != null) updatedDiscussion.setBody(dis.getBody());
            discussionService.createDiscussion(principal, updatedDiscussion); // Сохраняем обновленную dis
        }
        return "redirect:/discussions";
    }
    @GetMapping("/searchDiscussions")
    public String searchNote(@RequestParam String heading, Model model){
        model.addAttribute("listDiscussions", discussionService.getListByHeading(heading));
        return "disPages/searchDiscussion";
    }
}
