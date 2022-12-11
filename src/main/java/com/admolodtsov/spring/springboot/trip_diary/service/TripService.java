package com.admolodtsov.spring.springboot.trip_diary.service;

import com.admolodtsov.spring.springboot.trip_diary.entity.Trip;
import com.admolodtsov.spring.springboot.trip_diary.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


public interface TripService {
    public List<Trip> getAllTrips();

    public void saveTrip(Trip trip);

    public Trip findTripById(int id);

    public boolean existsById (int id);

    @PreAuthorize(value = "hasRole('ADMIN')" +
            "or authentication.name.equals(#trip.username)")
    public void deleteTrip(Trip trip);
    @PreAuthorize(value = "hasRole('ADMIN')" +
            "or authentication.name.equals(#trip.username)")
    public void updateTrip(Trip trip);

    public List<Trip> findAllByUser(User user);
}
