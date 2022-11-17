package com.admolodtsov.spring.springboot.trip_diary.controller;

import com.admolodtsov.spring.springboot.trip_diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String getUserList(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin-view";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                             @RequestParam(required = true, defaultValue = "" ) String action,
                             Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/get/{userId}")
    public String getUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.userGetList(userId));
        return "admin-view";
    }
}