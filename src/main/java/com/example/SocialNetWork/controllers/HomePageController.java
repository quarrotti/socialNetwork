package com.example.SocialNetWork.controllers;


import com.example.SocialNetWork.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    private UserRepository userRepository;
    
    @GetMapping("/homePage")
    public String homePage(){
        return "commonPages/home_page";
    }

    @GetMapping("/registration")
    public String registrationPage(){
        return "commonPages/Registry&Login/registration";
    }
    @GetMapping("/login")
    public String login(){
        return "commonPages/Registry&Login/login";
    }
    @GetMapping("/fail")
    public String fail(){
        return "commonPages/Registry&Login/fail";
    }

    @GetMapping("/")
    public String basePageForNotAuthenticated(){
        return "commonPages/BasePageForNotAuthenticated";
    }

}