package com.admolodtsov.spring.springboot.trip_diary.dao;

import com.admolodtsov.spring.springboot.trip_diary.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Integer> {
}