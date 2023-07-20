package com.personal.JavaAssignment.controller;

import com.personal.JavaAssignment.entity.Users;
import com.personal.JavaAssignment.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/login")
    public String login() {return "login";}

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") Users user) {
        System.out.println(user);
        usersService.save(user);
        return "reg_success";
    }

    @GetMapping("/registerForm")
    public String registrationForm(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "register";
    }


}
