package com.admolodtsov.spring.springboot.trip_diary.dao;


import com.admolodtsov.spring.springboot.trip_diary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}