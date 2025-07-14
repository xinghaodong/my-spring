package com.example.myspring.repository;

import com.example.myspring.entity.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaypointRepository extends JpaRepository<Waypoint, Integer> {
}