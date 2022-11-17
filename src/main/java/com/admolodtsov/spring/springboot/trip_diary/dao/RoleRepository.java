package com.admolodtsov.spring.springboot.trip_diary.dao;

import com.admolodtsov.spring.springboot.trip_diary.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}