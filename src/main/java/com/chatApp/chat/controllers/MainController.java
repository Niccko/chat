package com.chatApp.chat.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("title","ANGELINA");
        return "home";
    }
}
