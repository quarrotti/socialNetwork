package com.example.SocialNetWork.controllers;

import com.example.SocialNetWork.models.aboutDiscussion.DiscussionEntity;
import com.example.SocialNetWork.models.aboutNote.NoteEntity;
import com.example.SocialNetWork.services.DiscussionService;
import com.example.SocialNetWork.services.NoteService;
import com.example.SocialNetWork.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotesFeedController {
    private final NoteService noteService;
    private final DiscussionService discussionService;
    private final UserService userService;

    @GetMapping("/notes-feed")
    public String generalPage(Model model){
        List<NoteEntity> listNotes = noteService.listNotes();
        List<DiscussionEntity> listDiscussions = discussionService.allDiscussions();
        model.addAttribute("listNotes", listNotes);
        model.addAttribute("listDiscussions", listDiscussions);
        return "notePages/notes-feed";
    }
    @GetMapping("/allNotes")
    public String allNotes(Model model){
        List<NoteEntity> listNotes = noteService.listNotes();
        model.addAttribute("listNotes", listNotes);
        return "notePages/allNotes";
    }
    @PostMapping("/notes-feed/createNote")
    public String createNote(NoteEntity note, Principal principal){
        noteService.saveNote(principal,note);
        return "redirect:/allNotes";
    }
    @GetMapping("/commonPages/successPage")
    public String successOfCreatePage(){
        return "commonPages/successPage";
    }
    @PostMapping("/notes-feed/delete/{id}")
    public String deleteNote(@PathVariable Long id, Principal principal){
        if(userService.findByLogin(principal.getName()).equals(noteService.findById(id).getUser())){
            noteService.deleteNote(id);
            return "redirect:/notes-feed/successDelete";
        }
        return "redirect:/fail";
    }
    @GetMapping("/notes-feed/successDelete")
    public String successDelete(){
        return "commonPages/successDelete";
    }
    @GetMapping("/successPage")
    public String successPage() { return "commonPages/successPage"; }
    @GetMapping("/notes-feed/{id}")
    public String noteInfo(@PathVariable Long id, Model model) {
        NoteEntity note = noteService.findById(id);
        model.addAttribute("note", note);
        return "notePages/noteInfoCommon";
    }
    @GetMapping("/notes-feed/forCurrent/{id}")
    public String noteInfoC(@PathVariable Long id, Model model) {
        NoteEntity note = noteService.findById(id);
        model.addAttribute("note", note);
        return "notePages/noteInfoForCurrent";
    }
    @GetMapping("/notes-feed/edit/{id}")
    public String editNote(@PathVariable Long id, Principal principal, Model model) {
        NoteEntity note = noteService.findById(id);

        if (note.getUser().getLogin().equals(principal.getName())) {
            model.addAttribute("note" , note);
            return "notePages/updateNote";
        } else{
            return "redirect:/notes-feed";
        }

    }
    @PostMapping("/notes-feed/edit/{id}")
    public String editNoteP(@PathVariable Long id,
                            @RequestParam(required = false) String tag,
                            @RequestParam(required = false) String description,
                            Principal principal) {
        NoteEntity note = new NoteEntity();
        note.setTag(tag);
        note.setDescription(description);
        noteService.editNote(note, principal);
        return "redirect:/notes-feed";
    }
    @GetMapping("/searchNotes")
    public String searchNote(@RequestParam String tag, Model model){
        model.addAttribute("listNotes", noteService.getListByTag(tag));
        return "notePages/searchNotes"; //todo
    }


}
