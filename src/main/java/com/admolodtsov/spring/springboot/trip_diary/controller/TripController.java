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
    /* Отображение вида со списком постов текущего авторизованного пользователя */
    public String showMyTrips(Model model){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User authorizedUser = (User)authentication.getPrincipal();
        String currentUserName = authentication.getName();
        List<Trip> allMyTrips = tripService.findAllByUser(authorizedUser);
        model.addAttribute("allMyTrips", allMyTrips);
        String myTripListIsEmpty = "Пока что у вас нет добавленных статей...";
                if(allMyTrips.isEmpty()){
                    model.addAttribute("myTripListIsEmpty", myTripListIsEmpty);
                }
        return "my-trips-view";
    }

    @GetMapping("/trips/my/add")
    /* Отображение вида с формой для заполнения атрибутов и сохранения нового поста */
    public String newTripDetails(Model model){
        Trip trip = new Trip();
        model.addAttribute("trip",trip);
        return "trip-add-view";
    }
    @PostMapping("/trips/my/add")
    /* Создание нового объекта, присвоение ему введенных атрибутов из формы, получение имени
    авторизованного пользователя, присвоение его в качестве атрибута для нового объекта, сохранение нового
    объекта в БД*/
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
    /* Отображение вида с конкретным постом и его атрибутами по id, взятому из URL,
    увеличение счетчика числа просмотров для поста на 1 */
    public String showTripDetails(@PathVariable(value = "id") int id,
                                  Model model) {
        if(!tripService.existsById(id)) {
            return "redirect:/";
        }
        Trip trip = tripService.findTripById(id);
        model.addAttribute("trip", trip);
        trip.setViews(trip.getViews()+1);
        tripService.saveTrip(trip);
        return "trip-details-view";
    }

    @GetMapping("/trips/my/{id}")
       /* Отображение вида с конкретным постом авторизованного пользователя по id, взятому из URL */
    public String showMyTripDetails(@PathVariable(value = "id") int id,
                                  Model model) {
        if(!tripService.existsById(id)) {
            return "redirect:/";
        }
        Trip trip = tripService.findTripById(id);
        String authorName = trip.getUsername();
        model.addAttribute("trip", trip);
        model.addAttribute("authorName", authorName);
        return "my-trip-details-view";
    }


    @GetMapping("/trips/my/{id}/edit")
    /* Отображение вида с полями и введенными в них атрибутами поездки, найденной по id из URL адреса */
    public String editTripDetails(@PathVariable(value = "id") int id,
                                  Model model) {
        if(!tripService.existsById(id)) {
            return "redirect:/";
        }
        Trip trip = tripService.findTripById(id);
        model.addAttribute("trip", trip);
        return "trip-edit-view";
    }

    @PostMapping("/trips/my/{id}/edit")
    /* Сохранение существующей поездки с id из URL адреса, с атрибутами, взятыми из формы */
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
        tripService.updateTrip(trip);
        return "redirect:/trips/my/{id}";
    }

    @PostMapping("/trips/my/{id}/remove")
    /* Удаление поездки по id, указанному в URL-адресе */
    public String deleteTrip(@PathVariable(value = "id") int id){
        Trip trip = tripService.findTripById(id);
        tripService.deleteTrip(trip);
        return "redirect:/trips/my";
    }



}
