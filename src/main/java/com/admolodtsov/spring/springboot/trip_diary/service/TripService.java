package com.admolodtsov.spring.springboot.trip_diary.service;

import com.admolodtsov.spring.springboot.trip_diary.entity.Trip;
import com.admolodtsov.spring.springboot.trip_diary.entity.User;

import java.util.List;
import java.util.Optional;

public interface TripService {
    public List<Trip> getAllTrips();

    public void saveTrip(Trip trip);

    public Trip findTripById(int id);

    public boolean existsById (int id);

    public void deleteTrip(Trip trip);

    public List<Trip> findAllByUser(User user);
}
