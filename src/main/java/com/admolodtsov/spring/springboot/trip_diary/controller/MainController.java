package com.admolodtsov.spring.springboot.trip_diary.controller;

import com.admolodtsov.spring.springboot.trip_diary.entity.Trip;
import com.admolodtsov.spring.springboot.trip_diary.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class MainController {

    @Autowired
    private TripService tripService;

    @GetMapping("/")
    public String showMainView (Model model) {
        String authorizedName = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        String currentUserName = "гость";
                if (!authorizedName.equals("anonymousUser")){
                    currentUserName = authorizedName;
                };
        List<Trip> allTrips = tripService.getAllTrips();
        model.addAttribute("username", currentUserName);
        model.addAttribute("title", "Главная страница");
        model.addAttribute("allTrips", allTrips);
        return "main-view";
    }

    @GetMapping("/about")
    public String showAbout () {
        return "about-view";
    }






}
