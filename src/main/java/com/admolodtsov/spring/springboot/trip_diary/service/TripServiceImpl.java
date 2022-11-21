package com.admolodtsov.spring.springboot.trip_diary.service;

import com.admolodtsov.spring.springboot.trip_diary.dao.TripRepository;
import com.admolodtsov.spring.springboot.trip_diary.entity.Trip;
import com.admolodtsov.spring.springboot.trip_diary.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Trip findTripById(int id){
        Optional<Trip> tripFromDb= tripRepository.findById(id);
        return tripFromDb.orElse(new Trip());
    }

    @Override
    public boolean existsById (int id){
       return tripRepository.existsById(id);
    }

    @Override
    public void deleteTrip(Trip trip){
        tripRepository.delete(trip);
    }
}


