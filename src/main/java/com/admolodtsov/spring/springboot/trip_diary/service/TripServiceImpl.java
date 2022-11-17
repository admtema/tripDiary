package com.admolodtsov.spring.springboot.trip_diary.service;

import com.admolodtsov.spring.springboot.trip_diary.dao.TripRepository;
import com.admolodtsov.spring.springboot.trip_diary.entity.Trip;
import com.admolodtsov.spring.springboot.trip_diary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public void saveTrip(Trip trip) {
        tripRepository.save(trip);
    }
}


