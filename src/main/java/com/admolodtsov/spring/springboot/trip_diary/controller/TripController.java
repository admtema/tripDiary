package com.admolodtsov.spring.springboot.trip_diary.controller;

import com.admolodtsov.spring.springboot.trip_diary.entity.Trip;
import com.admolodtsov.spring.springboot.trip_diary.entity.User;
import com.admolodtsov.spring.springboot.trip_diary.service.TripService;
import com.admolodtsov.spring.springboot.trip_diary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TripController {
@Autowired
private TripService tripService;
@Autowired
private UserService userService;

    @GetMapping("/trips/my")
    public String showMyTrips(Model model){
        User authorizedUser = (User)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        List<Trip> allMyTrips = tripService.findAllByUser(authorizedUser);
        model.addAttribute("allMyTrips", allMyTrips);
        String myTripListIsEmpty = "Пока что у вас нет добавленных статей...";
                if(allMyTrips.isEmpty()){
                    model.addAttribute("myTripListIsEmpty", myTripListIsEmpty);
                }
        return "my-trips-view";
    }

    @GetMapping("/trips/add")
    public String showTripDetails(Model model){
        Trip trip = new Trip();
        model.addAttribute("employee",trip);
        return "trip-add-view";
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
        return "redirect:/trips/my";
    }
    @GetMapping("/trips/{id}")
    public String showTripDetails(@PathVariable(value = "id") int id,
                                  Model model) {
        if(!tripService.existsById(id)) {
            return "redirect:/";
        }
        Trip trip = tripService.findTripById(id);
        String authorName = trip.getUsername();
        model.addAttribute("trip", trip);
        model.addAttribute("authorName", authorName);
        trip.setViews(trip.getViews()+1);
        tripService.saveTrip(trip);
        return "trip-details-view";
    }

    @PreAuthorize("#authorName == authentication.name")
    @GetMapping("/trips/{id}/edit")
    public String editTripDetails(@PathVariable(value = "id") int id,
                                  Model model, @Param(value = "authorName") String name) {
        if(!tripService.existsById(id)) {
            return "redirect:/";
        }
        Trip trip = tripService.findTripById(id);
        String authorName = trip.getUsername();
        model.addAttribute("trip", trip);
        model.addAttribute("authorName", authorName);
        return "trip-edit-view";
    }

    @PostMapping("/trips/{id}/edit")
    public String updateTrip(@PathVariable(value = "id") int id,
                           @RequestParam String country,
                           @RequestParam String place,
                           @RequestParam String date,
                           @RequestParam int duration,
                           @RequestParam String story){
        Trip trip = tripService.findTripById(id);
        trip.setCountry(country);
        trip.setPlace(place);
        trip.setDate(date);
        trip.setDuration(duration);
        trip.setStory(story);
        tripService.saveTrip(trip);
        return "redirect:/trips/{id}";
    }

    @PostMapping("/trips/{id}/remove")
    public String deleteTrip(@PathVariable(value = "id") int id){
        Trip trip = tripService.findTripById(id);
        tripService.deleteTrip(trip);
        return "redirect:/trips/my";
    }



}
