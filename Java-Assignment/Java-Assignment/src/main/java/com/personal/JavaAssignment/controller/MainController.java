package com.personal.JavaAssignment.controller;

import com.personal.JavaAssignment.entity.Users;
import com.personal.JavaAssignment.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            if(usersService.findUserByUsername(user.getUsername()) == null) {
                usersService.save(user);
                return "reg_success";
            }else
                return "user_exists";
    }

    @GetMapping("/registerForm")
    public String registrationForm(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Users user = usersService.findUserByUsername(username);

        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("phoneNumber", user.getPhoneNumber());
        model.addAttribute("username", user.getUsername());

        return "profile";
    }



}
