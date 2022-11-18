package com.admolodtsov.spring.springboot.trip_diary.controller;

import com.admolodtsov.spring.springboot.trip_diary.entity.Trip;
import com.admolodtsov.spring.springboot.trip_diary.entity.User;
import com.admolodtsov.spring.springboot.trip_diary.service.TripService;
import com.admolodtsov.spring.springboot.trip_diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TripController {
@Autowired
private TripService tripService;
@Autowired
private UserService userService;

    @GetMapping("/trips")
    public String showAllTrips(Model model){
        List<Trip> allTrips = tripService.getAllTrips();
        model.addAttribute("allTrips", allTrips);
        return "trips-view";
    }

    @GetMapping("/trips/add")
    public String showTripDetails(Model model){
        Trip trip = new Trip();
        model.addAttribute("employee",trip);
        return "trip-add-or-edit-view";
    }
    @PostMapping("/trips/add")
    public String saveTrip(@RequestParam String country,
                          @RequestParam String place,
                          @RequestParam String date,
                          @RequestParam int duration,
                          @RequestParam String story){
        Trip trip = new Trip(country,place,date,duration,story);
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = (User)userService.loadUserByUsername(currentUserName);
        currentUser.addTripToUser(trip);
        tripService.saveTrip(trip);
        return "redirect:/trips";
    }
    @GetMapping("/trips/{id}")
    public String showTripDetails(@PathVariable(value = "id") long id,
                                  Model model) {
        Trip trip = tripService.findTripById(id);
        model.addAttribute("trip", trip);
        return "trip-details-view";
    }


}
